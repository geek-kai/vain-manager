package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;

/**
 * @author vain
 * @date： 2017/10/21 18:01
 * @description： 用户-角色对应表
 */
public class UserRole extends PagedEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    private Timestamp createTime;

    private Timestamp modifyTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
