package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.ScheduleJob;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vain
 * @Descritpion 定时任务的dao类
 * @Date 21:23 2017/10/31
 */
@Repository
public class ScheduleJobDao extends AbstractBaseDao<ScheduleJob> {

    @Override
    public PageList<ScheduleJob> getPagedList(ScheduleJob entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<ScheduleJob> getList(ScheduleJob entity) {
        return this.sqlSession.selectList("com.vain.manager.entity.ScheduleJob.getList", entity);
    }

    @Override
    public ScheduleJob get(ScheduleJob entity) {
        return this.sqlSession.selectOne("com.vain.manager.entity.ScheduleJob.get", entity);
    }

    @Override
    public int insert(ScheduleJob entity) {
        return 0;
    }

    @Override
    public int update(ScheduleJob entity) {
        return this.sqlSession.update("com.vain.manager.entity.ScheduleJob.modify", entity);
    }

    @Override
    public int delete(ScheduleJob entity) {
        return this.sqlSession.delete("com.vain.manager.entity.ScheduleJob.delete", entity);
    }
}
