package com.vain.manager.service;

import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.ScheduleJob;

public interface IScheduleJobService extends BaseService<ScheduleJob> {

    /**
     * 触发一次任务
     *
     * @param job
     */
    void triggerJob(ScheduleJob job);

    /**
     * 重新开始任务
     *
     * @param job
     */
    void resumeJob(ScheduleJob job);

    /**
     * 暂停任务
     *
     * @param job
     */
    void pauseJob(ScheduleJob job);
}
