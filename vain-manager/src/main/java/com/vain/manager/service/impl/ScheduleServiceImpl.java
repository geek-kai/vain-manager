package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.dao.ScheduleDao;
import com.vain.manager.entity.ScheduleJob;
import com.vain.manager.quartz.QuartzJobFactory;
import com.vain.manager.quartz.QuartzJobFactoryDisallowConcurrent;
import com.vain.manager.service.IScheduleJobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vain
 * @date： 2017/10/31 11:53
 * @description：
 */
@Service
public class ScheduleServiceImpl extends AbstractBaseService implements IScheduleJobService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public PageList<ScheduleJob> getPagedList(ScheduleJob entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public List<ScheduleJob> getList(ScheduleJob entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public ScheduleJob get(ScheduleJob entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public int add(ScheduleJob entity) throws ErrorCodeException {
        if (entity == null || entity.getJobStatus() != SysConstants.ENUMTASK.ISRUN.getState())
            return 0;
        try {
            addJob(entity);
        } catch (SchedulerException e) {
            logger.info("任务名称 = [" + entity.getJobName() + "] 添加失败 " +e.getMessage());
            throwErrorCodeException(SysConstants.Code.TASK_CRON_EXPRESSION_ERROR_CODE, SysConstants.Code.TASK_CRON_EXPRESSION_ERROR_MSG);
        }
        return 1;
    }

    @Override
    public int modify(ScheduleJob entity) throws ErrorCodeException {
        return 0;
    }

    @Override
    public int delete(ScheduleJob entity) throws ErrorCodeException {
        return 0;
    }

    /**
     * 添加任务
     *
     * @param job
     */
    private void addJob(ScheduleJob job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (null == trigger) {
            //任务不存在 注册一个
            Class clazz = job.getIsConcurrent() == (SysConstants.ENUMTASK.ISCONCURRENT.getState()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrent.class;
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", job);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger); //添加任务详情 触发器
            logger.info("任务名称 = [" + job.getJobName() + "] 新建成功");
        } else {
            //存在 仅需更新设置
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(cronScheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
            logger.info("任务名称 = [" + job.getJobName() + "] 修改成功");
        }
    }

    /**
     * 暂时任务
     *
     * @param job
     * @throws SchedulerException
     */
    private void pauseJob(ScheduleJob job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 回复任务
     *
     * @param job
     * @throws SchedulerException
     */
    private void resumeJob(ScheduleJob job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除任务
     *
     * @param job
     * @throws SchedulerException
     */
    private void deleteJob(ScheduleJob job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 触发执行一次任务
     *
     * @param job
     * @throws SchedulerException
     */
    private void triggerJob(ScheduleJob job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新cron表达式
     *
     * @param job
     * @throws SchedulerException
     */
    @SuppressWarnings("all")
    private void updateJobCronExpression(ScheduleJob job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
}
