package com.vain.manager.quartz;

import com.vain.manager.entity.ScheduleJob;
import com.vain.manager.util.SpringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author vain
 * @date： 2017/10/31 11:23
 * @description 通过反射调用任务job执行方法
 */
class TaskUtils {
    private final static Logger log = Logger.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    static void invokeMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;
        if (StringUtils.isNotBlank(scheduleJob.getSpringName())) {
            object = SpringUtils.getBean(scheduleJob.getSpringName());
        } else if (StringUtils.isNotBlank(scheduleJob.getJobClass())) {
            try {
                clazz = Class.forName(scheduleJob.getJobClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (object == null) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确------");
            return;
        }
        Method method = null;
        try {
            method = object.getClass().getDeclaredMethod(scheduleJob.getJobMethod());
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误---------");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        log.info("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功----------");
    }
}
