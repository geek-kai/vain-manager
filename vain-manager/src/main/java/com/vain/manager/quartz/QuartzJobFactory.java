package com.vain.manager.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vain
 * @date 2017/10/29 17:19
 * @description
 */
public class QuartzJobFactory implements Job {


    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        logger.info("任务名称 = [" + scheduleJob.getJobName() + "]");
    }

  /*  *//**
     * Job实现类  无状态
     * 若此方法上一次还未执行完，而下一次执行时间轮到时则该方法也可并发执行
     * @author root
     *//*
    public class QuartzJobFactory implements Job {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            QuartzJobBean scheduleJob = (QuartzJobBean)context.getMergedJobDataMap().get("scheduleJob");
            logger.info("运行任务名称 = [" + scheduleJob.getJobName() + "]");
            TaskUtils.invokMethod(scheduleJob);
        }

    }*/
}
