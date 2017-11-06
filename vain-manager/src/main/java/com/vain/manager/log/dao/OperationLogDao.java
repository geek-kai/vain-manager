package com.vain.manager.log.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.log.entity.OperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationLogDao extends AbstractBaseDao<OperationLog> {

    @Override
    public PageList<OperationLog> getPagedList(OperationLog entity, int curPage, int pageSize) {
        PageBounds pageBounds = new PageBounds(curPage, pageSize);
        return (PageList) this.sqlSession.selectList("com.vain.manager.log.entity.OperationLog.getPagedList", entity, pageBounds);
    }

    @Override
    public List<OperationLog> getList(OperationLog entity) {
        return null;
    }

    @Override
    public OperationLog get(OperationLog entity) {
        return null;
    }

    @Override
    public int insert(OperationLog entity) {
        return this.sqlSession.insert("com.vain.manager.log.entity.OperationLog.insert", entity);
    }

    @Override
    public int update(OperationLog entity) {
        return 0;
    }

    @Override
    public int delete(OperationLog entity) {
        return 0;
    }
}
