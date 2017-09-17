package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.SystemConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 系统组件dao
 * @author  vain
 * @date 2017/8/31 11:56
 */
@Repository
public class SysConfigDao extends AbstractBaseDao<SystemConfig> {

    @Override
    public PageList<SystemConfig> getPagedList(SystemConfig entity, int curPage, int pageSize) {
        PageBounds pageBounds = new PageBounds(curPage, pageSize);
        return (PageList) sqlSession.selectList("com.vain.manager.entity.SystemConfig.getPageList", entity, pageBounds);
    }

    @Override
    public List<SystemConfig> getList(SystemConfig entity) {
        return sqlSession.selectList("com.vain.manager.entity.SystemConfig.getList", entity);
    }

    @Override
    public SystemConfig get(SystemConfig entity) {
        return sqlSession.selectOne("com.vain.manager.entity.SystemConfig.get",entity);
    }

    @Override
    public int insert(SystemConfig entity) {
        return sqlSession.insert("com.vain.manager.entity.SystemConfig.insert", entity);
    }

    @Override
    public int update(SystemConfig entity) {
        return sqlSession.update("com.vain.manager.entity.SystemConfig.update", entity);
    }

    @Override
    public int delete(SystemConfig entity) {
        return 0;
    }

}
