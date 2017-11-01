package com.vain.manager.quartz;

import com.vain.manager.util.DBUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component(value = "autoTask")
public class AutoTask {

    private final static Logger log = Logger.getLogger(AutoTask.class);

    public void dbDump() {
        try {
            DBUtils.dumpSQL("C:\\Users\\Administrator\\Desktop\\vain.sql", "root", "ikaycity", "vain");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库备份失败" + e.getMessage());
        }
    }

    public void dbRestore() {
        try {
            DBUtils.restore("vain1", "root", "ikaycity", "C:\\Users\\Administrator\\Desktop\\vain.sql");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库还原失败" + e.getMessage());
        }
    }
}
