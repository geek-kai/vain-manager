import java.io.IOException;

/**
 * @author vain
 * @date 2017/10/29 16:15
 * @description
 */
public class DumpSql {

    private static String DB_USER = "root";

    private static String DB_PWD = "123";

    private static String DB_NAME = "vain";

    public static void main(String[] args) {
        //mysqldump -umysql -pmysql --add-drop-database --skip-lock-tables -B tjfinal > d:/backup.sql
      /*  String savePath = "C:\\Users\\qjy\\Desktop\\" + "backup-" + "11" + ".sql";
        String[] execCMD = new String[]{"mysqldump", "-u" + DB_USER, "-p" + DB_PWD, DB_NAME,
                "-r" + savePath, "--skip-lock-tables"};
        for (String data :
                execCMD) {
            System.out.println("data = " + data);
        }
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(execCMD);
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("备份成功.");
            } else {
                throw new RuntimeException("备份数据库失败.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            restore();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void restore() throws InterruptedException {
        //mysql -umysql -pmysql -e source d:/backup.sql
        String targetFile = "C:\\Users\\qjy\\Desktop\\backup-11.sql";  // SQL文件路径
        String[] execCMD = new String[]{"mysql", "vain", "-u" + "root", "-p" + "123", "-e source", targetFile};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(execCMD);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int processComplete = process.waitFor();
        if (processComplete == 0) {
            System.out.println("还原成功.");
        } else {
            throw new RuntimeException("还原数据库失败.");
        }
    }
}
