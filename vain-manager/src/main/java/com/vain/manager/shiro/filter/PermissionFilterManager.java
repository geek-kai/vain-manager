package com.vain.manager.shiro.filter;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;

import javax.servlet.FilterConfig;

/**
 * Created by vain on 2017/9/20.
 * filterManager管理
 */
public class PermissionFilterManager extends DefaultFilterChainManager {

    public PermissionFilterManager() {
    }

    public PermissionFilterManager(FilterConfig filterConfig) {
        super(filterConfig);
    }

    /**
     * shiro 配置文件中获取filter名称
     */
    private String filterName;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void init() {

    }
}
