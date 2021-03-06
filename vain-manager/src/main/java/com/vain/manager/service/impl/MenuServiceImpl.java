package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.dao.MenuDao;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.Role;
import com.vain.manager.entity.User;
import com.vain.manager.service.IMenuService;
import com.vain.manager.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by vain on 2017/9/23.
 */
@Service
public class MenuServiceImpl extends AbstractBaseService implements IMenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * 仅获取用户菜单集合
     *
     * @param user 用户id
     * @return 带层级结构的菜单列表
     * @throws ErrorCodeException
     */
    @Override
    public List<Menu> getMenusByUser(User user) throws ErrorCodeException {
        Menu menu = new Menu();
        menu.setUserId(user.getId());
        List<Menu> menus = menuDao.getMenusByUserId(menu);
        //递归返回层级结构的菜单列表
        List<Menu> returnMenus = new ArrayList<>();
        if (menus.size() > 0) {
            for (Menu data : menus) {
                if (data.getParentId() == SysConstants.MenuConstant.PARENT_ID_OF_NO_PARENT) {
                    returnMenus.add(data);
                    fillMenuListChildren(menus, data);
                }
            }
        } else {
            throwErrorCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        }

        return returnMenus;
    }

    @Override
    public HashSet<Menu> getMenusByUserId(Long userId, Integer userType) {
        HashSet<Menu> userOwnedMenus = new HashSet<>(); //去重 不包含重复元素菜单
        Menu menu = new Menu(userId);
        if (userType == SysConstants.AccountConstant.ACCOUNT_TYPE_SUPERADMIN) {
            //超级管理员权限菜单集合
            List<Menu> menusByUserRoles = menuDao.getMenusByUserRoles(menu);
            if (menusByUserRoles.size() > 0)
                userOwnedMenus.addAll(menusByUserRoles);
        } else {
            //用户所属角色菜单集合
            List<Menu> menusByUserRoles = menuDao.getMenusByUserRoles(menu);
            if (menusByUserRoles.size() > 0)
                userOwnedMenus.addAll(menusByUserRoles);
            //用户个人菜单集合
            List<Menu> userAllMenus = menuDao.getUserAllMenus(menu);
            if (userAllMenus.size() > 0)
                userOwnedMenus.addAll(userAllMenus);
        }
        return userOwnedMenus;
    }

    @Override
    public PageList<Menu> getPagedList(Menu entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public List<Menu> getList(Menu entity) throws ErrorCodeException {
        return null;
    }

    /**
     * 获取所有的菜单数据集合
     *
     * @param entity      参数实体
     * @param isHierarchy 是否返回层级结构 父子
     * @return
     * @throws ErrorCodeException
     */
    @Override
    public List<Menu> getList(Menu entity, boolean isHierarchy) throws ErrorCodeException {
        List<Menu> menus = menuDao.getList(entity); //所有菜单集合
        List<Menu> containsChildMenus = new ArrayList<>(); //所有菜单集合 分层级 父子结构
        if (menus.size() > 0) {
            if (isHierarchy) {
                for (Menu data : menus) {
                    if (data.getParentId() == SysConstants.MenuConstant.PARENT_ID_OF_NO_PARENT) { //主菜单
                        containsChildMenus.add(data);
                        fillMenuListChildren(menus, data);
                    }
                }
            } else {
                return menus;
            }
            logger.info("初始化的总菜单个数为：" + menus.size());
        } else {
            throw new ErrorCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        }
        return containsChildMenus;
    }

    /**
     * 获取自己菜单集合
     *
     * @param entity
     * @return
     * @throws ErrorCodeException
     */
    @Override
    public List<Menu> getMyMenus(Menu entity) throws ErrorCodeException {
        HashSet<Menu> userOwnedMenus = new HashSet<>(); //用户的自己菜单集合  采用set不允许重复
        List<Menu> returnMenus = new ArrayList<>(); //返回的菜单列表 不重复 带有层级结构
        /*
          用户type为1 默认角色为超级管理员 拥有其所有菜单
         */
        if (entity.getType() == SysConstants.AccountConstant.ACCOUNT_TYPE_SUPERADMIN) {
            logger.info("--------------超级管理员登录-------------");
            List<Menu> adminMenus = menuDao.getMenusByRoleKey(SysConstants.RoleConstant.SUPER_ADMINISTRATOR); //获取管理员的所有权限
            if (!adminMenus.isEmpty())
                userOwnedMenus.addAll(adminMenus);
            List<Menu> menus = new ArrayList<>(adminMenus);
            if (!menus.isEmpty()) {
                for (Menu data : menus) {
                    if (data.getParentId() == SysConstants.MenuConstant.PARENT_ID_OF_NO_PARENT) {
                        returnMenus.add(data);
                        fillMenuListChildren(menus, data);
                    }
                }
            }
        } else {
            /*
             超级管理员赋值的其他类型用户 如管理组和普通用户 获取角色和个人权限集合
             */
            logger.info("--------------其他类型登录-------------");
            List<Menu> menusByUserAllRoles = menuDao.getMenusByUserRoles(entity); //角色权限集合
            if (!menusByUserAllRoles.isEmpty())
                userOwnedMenus.addAll(menusByUserAllRoles);
            List<Menu> userAllMenus = menuDao.getUserAllMenus(entity);   //用户权限集合
            if (!userAllMenus.isEmpty())
                userOwnedMenus.addAll(userAllMenus);
            List<Menu> menus = new ArrayList<>(userOwnedMenus); // 转换为list  转换结构
            for (Menu data : menus) {  //递归转换数据结构
                if (data.getParentId() == SysConstants.MenuConstant.PARENT_ID_OF_NO_PARENT) {
                    if (!isContainMenu(returnMenus, data))
                        returnMenus.add(data);
                    fillMenuListChildren(menus, data);
                }
            }
        }

        //菜单集合为空 就返回相应的code 和 msg
        if (returnMenus.isEmpty())
            throw new ErrorCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        /*
         * 遍历菜单下子菜单 即二级菜单 并按照id进行排序
         */
        for (Menu data : returnMenus) {
            if (data.getChildren() != null && !data.getChildren().isEmpty()) {
                Collections.sort(data.getChildren(), new Comparator<Menu>() {
                    @Override
                    public int compare(Menu o1, Menu o2) {
                        return o1.getId() > o2.getId() ? 1 : -1;
                    }
                });
            }
        }

        Collections.sort(returnMenus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getId() > o2.getId() ? 1 : -1;
            }
        });

        return returnMenus;
    }

    /**
     * 根据role的id获取对应角色的所有权限列表
     *
     * @param entity
     * @return
     */
    @Override
    public List<Menu> getMenusByRoleId(Role entity) throws ErrorCodeException {
        Menu menu = new Menu();
        menu.setRoleId(entity.getId());
        List<Menu> returnMenus = new ArrayList<>();
        List<Menu> menus = menuDao.getMenusByRoleId(menu);
        if (!menus.isEmpty()) {
            for (Menu data : menus) {
                if (data.getParentId() == SysConstants.MenuConstant.PARENT_ID_OF_NO_PARENT) {
                    returnMenus.add(data);
                    fillMenuListChildren(menus, data);
                }
            }
        } else {
            throwErrorCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        }
        return returnMenus;
    }

    /**
     * 获取menukey 的子集菜单
     *
     * @param dataList
     * @param menuKey
     * @return
     */
    @Override
    public List<Menu> getChildMenu(List<Menu> dataList, String menuKey) {
        List<Menu> returnMenus = new ArrayList<>();
        if (StrUtil.isNotEmptyCollection(dataList)) {
            for (Menu data : dataList) {
                List<Menu> children = data.getChildren();
                if (StrUtil.isNotEmptyCollection(children)) {
                    for (Menu child : children) {
                        if (StrUtil.isNotEmpty(child.getMenuKey()) && child.getMenuKey().equals(menuKey)) {
                            if (StrUtil.isNotEmptyCollection(child.getChildren()))
                                returnMenus.addAll(child.getChildren());
                        }
                    }
                }

            }
        }
        return returnMenus;
    }

    @Override
    public Menu get(Menu entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public int add(Menu entity) throws ErrorCodeException {
        return 0;
    }

    @Override
    public int modify(Menu entity) throws ErrorCodeException {
        return menuDao.update(entity);
    }

    @Override
    public int delete(Menu entity) throws ErrorCodeException {
        return 0;
    }

    /**
     * 获取所有的菜单数据集合,采用递归操作,将子级菜单的分级的 fillMenuChildren:通过上级的菜单id，匹配其子级的菜单的parentId进行查询
     *
     * @param menu 上级菜单实体参数（菜单id）
     */
    private void fillMenuListChildren(List<Menu> menus, Menu menu) {
        List<Menu> childrenList = new ArrayList<>();
        /*
         * 遍历所有菜单集合 得到条件为父菜单id等于菜单parentId的所有子菜单 放入childrenList中，再将list集合set进父菜单的children里
         */
        for (Menu childMenu : menus) {
            if (childMenu.getParentId().equals(menu.getId())) {
                menu.setHasChildren(true);
                if (!isContainMenu(childrenList, childMenu))
                    childrenList.add(childMenu);
                menu.setChildren(childrenList);
                if (childMenu.getType() != SysConstants.MenuConstant.TYPE_OPERATE) {
                    fillMenuListChildren(menus, childMenu);
                }

            }

        }
    }


    /**
     * 是否已包含菜单
     *
     * @param menus
     * @param menu
     * @return
     */
    private boolean isContainMenu(List<Menu> menus, Menu menu) {
        if (menus == null || menus.isEmpty())
            return false;
        for (Menu data : menus) {
            if (data.getId().equals(menu.getId()))
                return true;
        }
        return false;
    }
}
