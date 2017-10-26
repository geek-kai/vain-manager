package com.vain.manager.shiro;

import org.apache.shiro.authz.Permission;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by vain on 2017/9/20.
 * 菜单权限上下文
 */
public class PermissionContext implements Serializable {

    private Set<String> resources;

    private Set<String> permissions;

    private Permission permissionObj;

    public Set<String> getResources() {
        return resources;
    }

    public void setResources(Set<String> resources) {
        this.resources = resources;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Permission getPermissionObj() {
        return permissionObj;
    }

    public void setPermissionObj(Permission permissionObj) {
        this.permissionObj = permissionObj;
    }
}
