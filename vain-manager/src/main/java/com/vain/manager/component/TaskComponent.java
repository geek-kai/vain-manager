package com.vain.manager.component;

import com.vain.manager.entity.ScheduleJob;
import com.vain.manager.service.IScheduleJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author vain
 * @date： 2017/10/31 11:28
 * @description： 定时任务组件
 */
@Component
public class TaskComponent {

    @Autowired
    private IScheduleJobService scheduleJobService;


    public void loadTaskFromDb() {
        for (int i = 0; i < 5; i++) {
            ScheduleJob job = new ScheduleJob();
            job.setId(Long.valueOf(i + ""));
            job.setJobName("data_import" + i);
            job.setJobGroup("dataWork");
            job.setJobStatus(1);
            job.setIsConcurrent(1);
            job.setCronExpression("0/5 * * * * ?");
            job.setDescription("数据导入任务");
            job.setJobClass("com.vain.manager.quartz.TaskDemo");
            job.setJobMethod("doSomething");
            try {
                scheduleJobService.add(job);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
