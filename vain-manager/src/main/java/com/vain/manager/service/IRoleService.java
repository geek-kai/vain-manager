package com.vain.manager.service;

import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.Role;

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
    void assignRoleMenu(Role entity);
}
