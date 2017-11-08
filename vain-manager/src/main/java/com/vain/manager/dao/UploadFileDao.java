package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.UploadFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vain
 * @date 2017/11/7 21:34
 * @description
 */
@Repository
public class UploadFileDao extends AbstractBaseDao<UploadFile> {

    @Override
    public PageList<UploadFile> getPagedList(UploadFile entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<UploadFile> getList(UploadFile entity) {
        return null;
    }

    @Override
    public UploadFile get(UploadFile entity) {
        return null;
    }

    @Override
    public int insert(UploadFile entity) {
        return this.sqlSession.insert("com.vain.manager.entity.UploadFile.insert", entity);
    }

    @Override
    public int update(UploadFile entity) {
        return 0;
    }

    @Override
    public int delete(UploadFile entity) {
        return 0;
    }
}
