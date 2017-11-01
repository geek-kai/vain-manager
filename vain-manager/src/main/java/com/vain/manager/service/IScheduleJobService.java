package com.vain.manager.service;

import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.ScheduleJob;
import org.quartz.SchedulerException;

public interface IScheduleJobService extends BaseService<ScheduleJob> {

    /**
     * 触发一次任务
     *
     * @param job
     */
    void triggerJob(ScheduleJob job) throws SchedulerException;

    /**
     * 重新开始任务
     *
     * @param job
     */
    void resumeJob(ScheduleJob job) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param job
     */
    void pauseJob(ScheduleJob job) throws SchedulerException;
}
