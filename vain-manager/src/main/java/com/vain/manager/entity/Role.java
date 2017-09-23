package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by vain on 2017/9/23.
 * 角色信息
 */
public class Role extends PagedEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色key
     */
    private String roleKey;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 删除标识符
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp modifyTime;

    /**
     * 角色权限集合列表
     */
    private List<Menu> menus;

    public Role() {
    }

    public Role(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
