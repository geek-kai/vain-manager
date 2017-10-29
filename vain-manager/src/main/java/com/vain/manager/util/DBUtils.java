package com.vain.manager.util;

/**
 * @author vain
 * @date 2017/10/29 16:53
 * @description 数据库操作帮助类
 */
public class DBUtils {

    /**
     * 保存数据库备份文件
     * cmd执行命令：mysqldump -uroot -p123 --add-drop-database --skip-lock-tables -B vain > d:/backup.sql
     *
     * @param savePath
     * @param dbUser
     * @param dbPWD
     * @param dbName
     */
    public static void dumpSQL(String savePath, String dbUser, String dbPWD, String dbName) throws Exception {
        String[] execCMD = new String[]{"mysqldump", "-u" + dbUser, "-p" + dbPWD, dbName,
                "-r" + savePath, "--skip-lock-tables"};
        Process process = Runtime.getRuntime().exec(execCMD);
        int processComplete = process.waitFor();
        if (processComplete != 0)
            throw new RuntimeException("备份数据库" + dbName + "失败");
    }


    /**
     * 恢复数据库备份
     * cmd执行命令：mysql -uroot -p123 -e source d:/backup.sql
     *
     * @param dbName   数据库名称
     * @param dbUser   数据库账号
     * @param dbPwd    数据库密码
     * @param filePath 备份SQL文件路径
     */
    public static void restore(String dbName, String dbUser, String dbPwd, String filePath) throws Exception {
        String[] execCMD = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPwd, "-e source", filePath};
        Process process = Runtime.getRuntime().exec(execCMD);
        int processComplete = process.waitFor();
        if (processComplete != 0)
            throw new RuntimeException("还原数据库失败.");

    }
}
