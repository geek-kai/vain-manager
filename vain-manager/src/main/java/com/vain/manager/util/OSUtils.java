package com.vain.manager.util;

import com.sun.management.OperatingSystemMXBean;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vain
 * @date 2017/10/15 15:03
 * @description 操作系统信息类 采取jdk来获取
 * 如果要获取详细的信息：如网卡驱动、操作系统详细信息、cpu所属信息、cpu每个核心使用率等信息
 * 可以通过sigar的jar包和其必须的dll文件来实现
 * 获取的服务器信息更加全面和准确
 * 下载地址：http://sourceforge.net/projects/sigar/files/latest/download?source=files
 * 相关博客地址：https://my.oschina.net/mkh/blog/312911
 */
public class OSUtils {
    private static final int CPUTIME = 5000; ///cpu测试时间段 时间越长 cpu使用百百分比约准确
    private static final int FAULTLENGTH = 10;

    public static Map<String, String> getInfo() {
        OperatingSystemMXBean osMXB = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map<String, String> infos = new LinkedHashMap<>();
        infos.put("服务器系统名称", System.getProperty("os.name"));
        infos.put("服务器账户名", System.getProperty("user.name"));
        infos.put("服务器系统版本", System.getProperty("os.version"));
        infos.put("总物理内存", Bit2Gb(osMXB.getTotalPhysicalMemorySize()) + " G");
        infos.put("剩余物理内存", Bit2Gb(osMXB.getFreePhysicalMemorySize()) + " G");
        infos.put("已使用内存", Bit2Gb((osMXB.getTotalPhysicalMemorySize() - osMXB.getFreePhysicalMemorySize())) + " G");
        infos.put("java版本", System.getProperty("java.version"));
        infos.put("java路径", System.getProperty("java.home"));
        infos.put("java字节码版本", System.getProperty("java.class.version"));
        infos.put("虚拟机名称", System.getProperty("java.vm.name"));
        infos.put("虚拟机版本号", System.getProperty("java.vm.version"));
        return infos;
    }


    // 获得cpu使用率
    public static int getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir")
                    + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] start = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] end = readCpu(Runtime.getRuntime().exec(procCmd));
            if (start != null && end != null) {
                long idletime = end[0] - start[0];
                long busytime = end[1] - start[1];
                return Double.valueOf(100 * (busytime) * 1.0 / (busytime + idletime)).intValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // 读取cpu相关信息
    private static long[] readCpu(final Process process) {
        long[] returnLong = new long[2];
        try {
            process.getOutputStream().close();
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(inputStreamReader);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0)
                        idletime += Long.valueOf(s1).longValue();
                    if (s2.length() > 0)
                        idletime += Long.valueOf(s2).longValue();
                    continue;
                }
                if (s1.length() > 0)
                    kneltime += Long.valueOf(s1.replace(" ", "")).longValue();
                if (s2.length() > 0)
                    usertime += Long.valueOf(s2.replace(" ", "")).longValue();
            }
            returnLong[0] = idletime; //空闲时间
            returnLong[1] = kneltime + usertime; //整体时间
            return returnLong;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                process.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     *
     * @param src       要截取的字符串
     * @param start_idx 开始坐标（包括该坐标)
     * @param end_idx   截止坐标（包括该坐标）
     * @return
     */
    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String returnStr = "";
        for (int i = start_idx; i <= end_idx; i++) {
            returnStr += (char) b[i];
        }
        return returnStr;
    }

    /**
     * 转换为GB 保留小数点1位
     *
     * @param bit
     * @return
     */
    private static float Bit2Gb(long bit) {
        return new BigDecimal(bit / 1024 / 1024).divide(new BigDecimal(1024)).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}

