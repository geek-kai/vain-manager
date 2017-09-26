package com.vain.manager.cache;

import com.vain.manager.common.entity.Response;
import com.vain.manager.entity.Menu;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vain
 * @date 2017/9/25 20:50
 * @description menu集合的缓存类，存于session中 所以更改菜单之后 需要重新登录才起作用
 */
public class PermissionCache {

    //存储的menu数据
    public Map<String, Response<Menu>> permissionCacheMap;

    //menu存储到map中的key
    public static final String MENU_KEY = "root";

    //session 存储的key
    public static final String SESSION_KEY_PERMISSION_CACHE = "permissionCache";

    public PermissionCache() {
        permissionCacheMap = new HashMap<>();
    }

    public void setMenus(Response<Menu> data) {
        permissionCacheMap.put(MENU_KEY, data);
    }

    public Response<Menu> getMenus() {
        return permissionCacheMap.get(MENU_KEY);
    }

    public void setMenusKey(String menuKey, Response<Menu> data) {
        permissionCacheMap.put(menuKey, data);
    }

    public Response<Menu> getMenusKey(String menuKey) {
        return permissionCacheMap.get(menuKey);
    }

    //清除
    public void destory() {
        permissionCacheMap.clear();
    }

    //创建map
    public static PermissionCache create() {
        return new PermissionCache();
    }
}
