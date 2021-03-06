package com.vain.manager.quartz;

import com.vain.manager.entity.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vain
 * @date： 2017/10/31 14:53
 * @description： 同步 不管上次是否执行完成
 */
public class QuartzJobFactory implements Job {


    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokeMethod(scheduleJob);
        logger.info("任务名称 = [" + scheduleJob.getJobName() + "]");
    }
}
