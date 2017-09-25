package com.vain.manager.shiro.filter;

import com.vain.manager.entity.Menu;
import com.vain.manager.service.IMenuService;
import com.vain.manager.util.StrUtil;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vain on 2017/9/20.
 * filterManager管理
 * 初始化时候读取菜单列表
 */
public class PermissionFilterManager extends DefaultFilterChainManager {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMenuService menuService;

    /**
     * 重试次数
     */
    private AtomicInteger tryCounts = new AtomicInteger(0);

    /**
     * shiro 配置文件中获取filter名称
     */
    private String filterName;

    public PermissionFilterManager() {
    }

    public PermissionFilterManager(FilterConfig filterConfig) {
        super(filterConfig);
    }


    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void init() {

        try {
            List<Menu> menus = menuService.getList(null, false);
            Map<String, String> urlPerms = new HashMap<>();
            for (Menu data : menus) {
                if (!StrUtil.isEmpty(data.getUrl())) {
                    urlPerms.put(data.getUrl(), data.getMenukey());
                }
                if (null == this.filterName)
                    this.filterName = "perms";
                for (Map.Entry<String, String> entry : urlPerms.entrySet()) {
                    this.addToChain(entry.getKey(), filterName, entry.getValue());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            logger.error("PermissionFilterManager init fail,try again!");
            if (tryCounts.get() < 5) {
                tryCounts.incrementAndGet();
                init();
            } else {
                logger.error("PermissionFilterManager init fail,Try to fail five times,Please check your code!");
            }
        }
    }
}
