package com.vain.manager.util;

import java.io.*;

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
     * @param dbPwd
     * @param dbName
     */
    public static void dumpSQL(String savePath, String dbUser, String dbPwd, String dbName) throws Exception {
        String[] execCMD = new String[]{"mysqldump", "-u" + dbUser, "-p" + dbPwd, dbName,
                "-r" + savePath, "--skip-lock-tables"};
        Process process = Runtime.getRuntime().exec(execCMD);
        int processComplete = process.waitFor();
        if (processComplete != 0)
            throw new Exception("备份数据库" + dbName + "失败");
    }


    /**
     * 恢复数据库备份
     * cmd执行命令：mysql -u root -pikaycity --default-character-set=utf8 vain
     *
     * @param dbName   数据库名称
     * @param dbUser   数据库账号
     * @param dbPwd    数据库密码
     * @param filePath 备份SQL文件路径
     */
    public static void restore(String dbName, String dbUser, String dbPwd, String filePath) throws Exception {
        try {
            String cmd = String.format("mysql -u %s -p%s --default-character-set=utf8 %s", dbUser, dbPwd, dbName);
            Process process = Runtime.getRuntime().exec(cmd);
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                sb.append(str).append("\r\n");
            }
            str = sb.toString();
            System.out.println(str);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("还原数据库" + dbName + "失败");
        }

    }
}
