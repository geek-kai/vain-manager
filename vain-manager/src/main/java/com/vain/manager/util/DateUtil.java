package com.vain.manager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author vain
 * @date： 2017/11/8 9:49
 * @description： 日期帮助类
 */
public class DateUtil {

    public static Date getCurDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return new Date(cal.getTimeInMillis());
    }

    /**
     * 按照给定格式将java.util.Date转换为字符串
     * 
     * @param date
     * @param formatStr
     *            yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date strTodate(String date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String fillWithDayStart(String time){
        return time + " 00:00:00";
    }
    
    public static String fillWithDayEnd(String time){
        return time + " 23:59:59";
    }
}
