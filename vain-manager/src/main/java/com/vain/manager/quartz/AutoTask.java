package com.vain.manager.quartz;

import com.vain.manager.component.SysConfigComponent;
import com.vain.manager.constant.SysConfigKeys;
import com.vain.manager.util.DBUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component(value = "autoTask")
public class AutoTask {

    @Autowired
    private SysConfigComponent sysConfigComponent;

    private final static Logger log = Logger.getLogger(AutoTask.class);

    public void dbDump() {
        try {
            DBUtils.dumpSQL(sysConfigComponent.getStringValue(SysConfigKeys.DB_DUMP_PATH) +
                            sysConfigComponent.getStringValue(SysConfigKeys.DB_DUMP_NAME) + new Date().toString() + ".sqld"
                    , "null", "null", sysConfigComponent.getStringValue(SysConfigKeys.DB_DUMP_NAME));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库备份失败" + e.getMessage());
        }
    }

    public void dbRestore() {
        try {
            DBUtils.restore(null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库还原失败" + e.getMessage());
        }
    }
}
