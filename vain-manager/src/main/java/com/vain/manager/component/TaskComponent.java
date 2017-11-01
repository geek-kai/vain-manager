package com.vain.manager.component;

import com.vain.manager.entity.ScheduleJob;
import com.vain.manager.service.IScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<ScheduleJob> list = scheduleJobService.getList(null);
        for (ScheduleJob job : list) {
            scheduleJobService.add(job);
        }
    }
}
