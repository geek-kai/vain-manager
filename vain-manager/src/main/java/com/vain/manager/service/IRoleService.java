package com.vain.manager.service;

import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.Role;
import com.vain.manager.entity.UserRole;

/**
 * @author vain
 * @date 2017/10/9 20:00
 * @description
 */
public interface IRoleService extends BaseService<Role> {
    /**
     * 给角色分配权限菜单
     *
     * @param entity
     */
    int assignRoleMenu(Role entity);

    /**
     * 给账号分配角色
     *
     * @param entity
     * @return
     */
    int grantUserRole(UserRole entity);
}
