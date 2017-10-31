package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.ScheduleJob;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleDao extends AbstractBaseDao<ScheduleJob> {
    @Override
    public PageList<ScheduleJob> getPagedList(ScheduleJob entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<ScheduleJob> getList(ScheduleJob entity) {
        return null;
    }

    @Override
    public ScheduleJob get(ScheduleJob entity) {
        return null;
    }

    @Override
    public int insert(ScheduleJob entity) {
        return 0;
    }

    @Override
    public int update(ScheduleJob entity) {
        return 0;
    }

    @Override
    public int delete(ScheduleJob entity) {
        return 0;
    }
}
