package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.dao.UserDao;
import com.vain.manager.dao.UserMenuDao;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.SystemConfig;
import com.vain.manager.entity.User;
import com.vain.manager.entity.UserMenu;
import com.vain.manager.service.IUserService;
import com.vain.manager.util.MD5Util;
import com.vain.manager.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author vain
 * @description: 用户信息service
 * @date 2017/8/31 12:01
 */
@Service
public class UserServiceImpl extends AbstractBaseService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMenuDao userMenuDao;

    @Override
    public User login(User entity) throws ErrorCodeException {
        if (StrUtil.isEmail(entity.getUserName())) { //通过邮箱登录
            entity.setEmail(entity.getUserName());
            entity.setUserName(null);
        } else if (StrUtil.isNumeric(entity.getUserName())) { //通过手机号码登录
            entity.setPhone(entity.getUserName());
            entity.setUserName(null);
        }
        User dbUser = userDao.get(entity);
        if (null == dbUser) {
            // 登录失败，账号不存在
            logger.error("login failure，this account does not exist");
            throwErrorCodeException(SysConstants.Code.ACCOUNT_IS_NOT_EXISTS_CODE, SysConstants.Code.ACCOUNT_IS_NOT_EXISTS_MSG);
        }
        if (dbUser.getState() == SysConstants.AccountConstant.STATUS_LOCKED) {
            // 登录失败，账户被禁用 后期可根据state来扩展
            logger.error("login failure，this account is locked");
            throwErrorCodeException(SysConstants.Code.ACCOUNT_IS_LOCKED_CODE, SysConstants.Code.ACCOUNT_IS_LOCKED_MSG);
        }
        if (dbUser.getPasswd().equals(MD5Util.getMD5Str(entity.getPasswd() + dbUser.getSalt()))) {
            dbUser.clearSecretField();//清除敏感字段
            return dbUser;
        }
        // 登录失败，账号或密码不正确
        logger.error("login failure，this account or password is error");
        throwErrorCodeException(SysConstants.Code.ACCOUNT_OR_PASSWORD_ERROR_CODE, SysConstants.Code.ACCOUNT_OR_PASSWORD_ERROR_MSG);
        return null;
    }

    /**
     * 重置密码
     *
     * @param entity
     * @return
     */
    @Override
    public int resetPwd(User entity) {
        String salt = UUID.randomUUID().toString();
        entity.setSalt(UUID.randomUUID().toString());
        entity.setPasswd(MD5Util.getMD5Str(entity.getNewpasswd() + salt));
        return userDao.resetPwd(entity);
    }

    /**
     * 锁定 / 解锁 账号
     *
     * @param entity
     * @return
     */
    @Override
    public int lock(User entity) {
        return userDao.lock(entity);
    }

    /**
     * 分配用户权限
     * 先删除原来的权限列表 在添加新的权限列表
     *
     * @param entity
     */
    @Override
    public int assignUserMenu(User entity) {
        UserMenu userMenu = new UserMenu();
        List<UserMenu> userMenus = new ArrayList<>();
        userMenu.setUserId(entity.getId());
        userMenuDao.delete(userMenu);
        List<Menu> menus = entity.getMenus();
        for (Menu menu : menus) {
            assignPermission(menu, entity, userMenus);
        }
        if (userMenus.size() > 0) {
            return userMenuDao.assignUserMenu(userMenus);
        }
        return 0;
    }

    /**
     * 分配用户菜单权限递归的方法
     *
     * @param menu，传入遍历的单个菜单数据
     * @param user，用户id
     */
    public void assignPermission(Menu menu, User user, List<UserMenu> userMenus) {
        if (menu.getHasPermission() != null && menu.getHasPermission()) {
            /*
             * 添加，修改为同一方法，修改进来，默认选中 根据menuId和roleId重新添加
             */
            UserMenu userMenu = new UserMenu();
            userMenu.setMenuId(menu.getId());
            userMenu.setUserId(user.getId());
            userMenus.add(userMenu);
            /*
             * 再次进行递归 第3级判断
             */
            if (menu.getChildren() != null && menu.getChildren().size() > 0) {
                List<Menu> menuChilds = menu.getChildren();
                for (Menu menuChild : menuChilds) {
                    assignPermission(menuChild, user, userMenus);
                }
            }
        }
    }

    @Override
    public PageList<User> getPagedList(User entity) throws ErrorCodeException {
        entity.initPageParam();
        return userDao.getPagedList(entity, entity.getCurPage(), entity.getPageSize());
    }

    @Override
    public List<User> getList(User entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public User get(User entity) throws ErrorCodeException {
        return null;
    }

    /**
     * 添加账号
     *
     * @param entity 参数实体
     * @throws ErrorCodeException
     */
    @Override
    public int add(User entity) throws ErrorCodeException {
        entity.setSalt(UUID.randomUUID().toString());
        entity.setPasswd(MD5Util.getMD5Str(entity.getPasswd() + entity.getSalt()));
        entity.setState(SysConstants.AccountConstant.STATUS_UN_LOCKED);
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        entity.setDeleted(SysConstants.DeletedStatus.STATUS_UN_DELETED);
        return userDao.insert(entity);
    }

    /**
     * 修改账号信息
     *
     * @param entity 参数实体
     * @return
     * @throws ErrorCodeException
     */
    @Override
    public int modify(User entity) throws ErrorCodeException {
        return userDao.update(entity);
    }

    /**
     * 删除账号
     *
     * @param entity 参数实体
     * @throws ErrorCodeException
     */
    @Override
    public int delete(User entity) throws ErrorCodeException {
        entity.setDeleted(SysConstants.DeletedStatus.STATUS_DELETED);
        return userDao.delete(entity);
    }
}