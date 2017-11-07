package com.vain.manager.component;

import com.alibaba.fastjson.JSON;
import com.vain.manager.dao.SystemConfigDao;
import com.vain.manager.entity.SystemConfig;
import com.vain.manager.listener.StartupListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.util.HashMap;
import java.util.List;

/**
 * @author vain
 * @description: 系统配置组件  在系统启动的时候读取系统一些不常修改的配置信息
 * @date 2017/8/31 11:52
 */
@Component
public class SysConfigComponent {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    private SystemConfigDao sysConfigDao;

    private HashMap<String, String> configMapFromDb = new HashMap<>();

    public void loadSystemConfigFromDb() {
        List<SystemConfig> sysConfigs = sysConfigDao.getList(null);
        for (SystemConfig sysConfig : sysConfigs) {
            configMapFromDb.put(sysConfig.getCode(), sysConfig.getValue());
        }
    }

    public String getStringValue(String key) {
        return configMapFromDb.get(key);
    }

    public int getIntValue(String key) {
        return Integer.parseInt(getStringValue(key));
    }

    public String[] getSringArrayValue(String key) {
        return getStringValue(key).split("\\,");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    private static SysConfigComponent instance;

    public static SysConfigComponent instance() {
        if (instance == null) {
            instance = ContextLoader.getCurrentWebApplicationContext().getBean(SysConfigComponent.class);
        }

        return instance;
    }

    /**
     * 在修改后重新加载数据
     */
    public synchronized void reload() {
        configMapFromDb = new HashMap<>();
        logger.info("------------------重新加载系统配置信息------------------");
        loadSystemConfigFromDb();//重新加载
    }

}
