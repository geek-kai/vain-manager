package com.vain.manager.service;


import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.User;

/**
 * @author vain
 * @description: 用户service类
 * @date 2017/8/31 15:23
 */

public interface IUserService extends BaseService<User> {
    /**
     * 用户登录
     *
     * @param entity
     * @return
     */
    User login(User entity) throws ErrorRCodeException;

    /**
     * 重置密码
     *
     * @param entity
     * @return
     */
    int resetPwd(User entity);

    /**
     * 锁定 / 解锁用户
     *
     * @param entity
     * @return
     */
    int lock(User entity);
}
