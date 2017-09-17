package com.vain.manager.service;


import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.User;

/**
 * @description: 用户service类
 * @author  vain
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

}
