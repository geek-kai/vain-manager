package com.vain.manager.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 生成用户账号密码加盐字符串
     *
     * @return
     */
    public static String generateUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * 判断输入对象是否为空（null/空字符串）
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return str == null || str.toString().isEmpty() || str.toString().length() == 0;
    }

    public static boolean isNotEmpty(Object str) {
        return !StrUtil.isEmpty(str);
    }

    /**
     * 取字符串中的所有中文
     *
     * @return
     */
    public static String getAllCN(String str) {
        StringBuilder sb = new StringBuilder();
        Pattern p = null;
        Matcher m = null;
        String value = null;
        p = Pattern.compile("([\u4e00-\u9fa5]+)");
        m = p.matcher(str);
        while (m.find()) {
            value = m.group(0);
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 截取指定字节长度的字符串，不能返回半个汉字
     *
     * @return
     */
    public static String getSubString(String str, int length) {
        int count = 0;
        int offset = 0;
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] > 256) {
                offset = 2;
                count += 2;
            } else {
                offset = 1;
                count++;
            }
            if (count == length) {
                return str.substring(0, i + 1);
            }
            if ((count == length + 1 && offset == 2)) {
                return str.substring(0, i);
            }
        }
        return "";
    }

    /**
     * 返回str第一个中文
     *
     * @return
     */
    public static String getFirstCN(String str) {
        return getSubString(getAllCN(str), 2);
    }

    /**
     * 字符串首字母变小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 字符串首字母变大写
     *
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.equals(""))
            return false;
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为邮箱地址
     *
     * @return
     */
    public static boolean isEmail(String str) {
        return str != null && str.contains("@");
    }
}
