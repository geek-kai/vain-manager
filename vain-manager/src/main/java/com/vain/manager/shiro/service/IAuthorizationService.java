package com.vain.manager.shiro.service;

import com.vain.manager.shiro.PermissionContext;
import com.vain.manager.shiro.authenticator.SubjectInfo;

/**
 * Created by vain on 2017/9/20.
 */
public interface IAuthorizationService {

    /**
     * 通过身份获取权限列表
     *
     * @param subjectInfo
     * @return
     */
    PermissionContext getPermissions(SubjectInfo subjectInfo);
}
