package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.dao.MenuDao;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.User;
import com.vain.manager.service.IMenuService;
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

    @Override
    public List<Menu> getUserMenuByUser(User user) throws ErrorRCodeException {
        return null;
    }

    @Override
    public HashSet<Menu> getMenusByUserId(Long userId, Integer userType) {
        HashSet<Menu> userOwnedMenus = new HashSet<>();
        Menu menu = new Menu(userId);
        if (userType == SysConstants.AccountConstant.ACCOUNT_TYPE_USER) {
            List<Menu> menusByUserRoles = menuDao.getMenusByUserRoles(menu);
            if (menusByUserRoles.size() > 0)
                userOwnedMenus.addAll(menusByUserRoles);
            List<Menu> userAllMenus = menuDao.getUserAllMenus(menu);
            if (userAllMenus.size() > 0)
                userOwnedMenus.addAll(userAllMenus);
        } else {
            List<Menu> menusByUserRoles = menuDao.getMenusByUserRoles(menu);
            if (menusByUserRoles.size() > 0)
                userOwnedMenus.addAll(menusByUserRoles);
        }
        return userOwnedMenus;
    }

    @Override
    public PageList<Menu> getPagedList(Menu entity) throws ErrorRCodeException {
        return null;
    }

    @Override
    public List<Menu> getList(Menu entity) throws ErrorRCodeException {
        return null;
    }

    /**
     * 获取所有的菜单数据集合
     *
     * @param entity      参数实体
     * @param isHierarchy 是否返回层级结构 父子
     * @return
     * @throws ErrorRCodeException
     */
    @Override
    public List<Menu> getList(Menu entity, boolean isHierarchy) throws ErrorRCodeException {
        List<Menu> menus = menuDao.getList(entity); //所有菜单集合
        List<Menu> containsChildMenus = menuDao.getList(entity); //所有菜单集合 分层级 父子结构
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
            throw new ErrorRCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        }
        return containsChildMenus;
    }

    @Override
    public List<Menu> getMyMenus(Menu entity) throws ErrorRCodeException {
        HashSet<Menu> userOwnedMenus = new HashSet<>(); //用户的自己菜单集合  采用set不允许重复
        List<Menu> returnMenus = new ArrayList<>(); //返回的菜单列表 不重复 带有层级结构
        /*
         * 普通的用户登录
         */
        if (entity.getType() == SysConstants.AccountConstant.ACCOUNT_TYPE_USER) {
            logger.info("--------------普通用户登录-------------");
            List<Menu> menusByUserAllRoles = menuDao.getMenusByUserRoles(entity); //角色权限集合
            if (!menusByUserAllRoles.isEmpty())
                userOwnedMenus.addAll(menusByUserAllRoles);
            List<Menu> userAllMenus = menuDao.getUserAllMenus(entity);   //用户权限集合
            if (!userAllMenus.isEmpty())
                userOwnedMenus.addAll(userAllMenus);
            List<Menu> menus = new ArrayList<>(userOwnedMenus); //去重之后 转换为list
            for (Menu data : menus) {  //递归转换数据结构
                if (data.getParentId() == SysConstants.MenuConstant.PARENT_ID_OF_NO_PARENT) {
                    returnMenus.add(data);
                    fillMenuListChildren(menus, data);
                }
            }
        /*
         * 管理员登录
         */
        } else {
            logger.info("--------------管理员登录-------------");
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
        }

        //菜单集合为空 就返回相应的code 和 msg
        if (returnMenus.isEmpty())
            throw new ErrorRCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
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

    @Override
    public Menu get(Menu entity) throws ErrorRCodeException {
        return null;
    }

    @Override
    public void add(Menu entity) throws ErrorRCodeException {

    }

    @Override
    public void modify(Menu entity) throws ErrorRCodeException {

    }

    @Override
    public void delete(Menu entity) throws ErrorRCodeException {

    }

    /**
     * 获取所有的菜单数据集合,子级菜单的数据查询的递归操作 fillMenuChildren:通过上级的菜单id，匹配其子级的菜单的parentId进行查询
     *
     * @param menu 上级菜单实体参数（菜单id）
     */
    private void fillMenuListChildren(List<Menu> menus, Menu menu) {
        List<Menu> childrenList = new ArrayList<Menu>();
        /*
         * 遍历所有菜单集合 得到条件为父菜单id等于菜单parentId的所有子菜单 放入childrenList中，再将list集合set进父菜单的children里
         */
        for (Menu childMenu : menus) {
            if (childMenu.getParentId().equals(menu.getId())) {
                menu.setHasChildren(true);
                menu.setChildren(childrenList);
                childrenList.add(childMenu);
                if (childMenu.getType() != SysConstants.MenuConstant.TYPE_OPERATE) {
                    fillMenuListChildren(menus, childMenu);
                }
            }
        }
    }
}
