package com.vain.manager.listener;

import com.vain.manager.component.SysConfigComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @description: 系统启动时读取组件信息
 * @author  vain
 * @date 2017/8/31 12:00
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    private SysConfigComponent sysConfigComponent;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            logger.info("startup completed");

            // 此处初始化数据库里保存的配置项
            sysConfigComponent.loadSystemConfigFromDb();
        }

    }
}