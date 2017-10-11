package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;

/**
 * @author vain
 * @date： 2017/10/11 15:04
 * @description： t_role_menu对应的实体类
 */
public class RoleMenu extends PagedEntity {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

    private Timestamp createTime;

    private Timestamp modifyTime;

    public RoleMenu() {
    }

    public RoleMenu(Long roleId) {
        this.roleId = roleId;
    }


    public RoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
}
