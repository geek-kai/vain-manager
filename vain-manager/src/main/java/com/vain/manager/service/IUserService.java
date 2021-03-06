package com.vain.manager.service;


import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.User;
import com.vain.manager.model.OnLineUser;

import java.util.List;

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
    User login(User entity) throws ErrorCodeException;

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

    /**
     * 分配用户菜单权限
     *
     * @param entity
     */
    int assignUserMenu(User entity);

    /**
     * 获取在线用户信息
     *
     * @return
     */
    List<OnLineUser> getOnLineUser();

    /**
     * 在线用户下线
     *
     * @param entity
     * @return
     */
    int forcedOffLine(OnLineUser entity);
}
