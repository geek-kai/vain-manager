package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.dao.UserDao;
import com.vain.manager.entity.User;
import com.vain.manager.service.IUserService;
import com.vain.manager.util.MD5Util;
import com.vain.manager.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void add(User entity) throws ErrorCodeException {

    }

    @Override
    public void modify(User entity) throws ErrorCodeException {

    }

    @Override
    public void delete(User entity) throws ErrorCodeException {

    }
}