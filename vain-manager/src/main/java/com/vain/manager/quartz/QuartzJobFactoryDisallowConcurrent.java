package com.vain.manager.quartz;

import com.vain.manager.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vain
 * @date 2017/10/29 17:19
 * @description 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作(禁止并发)
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrent implements Job {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokeMethod(scheduleJob);
        logger.info("任务名称 = [" + scheduleJob.getJobName() + "]");
    }
}
