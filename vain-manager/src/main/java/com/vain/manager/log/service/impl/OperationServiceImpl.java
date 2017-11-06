package com.vain.manager.log.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.log.dao.OperationLogDao;
import com.vain.manager.log.entity.OperationLog;
import com.vain.manager.log.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationServiceImpl extends AbstractBaseService implements IOperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;


    @Override
    public PageList<OperationLog> getPagedList(OperationLog entity) throws ErrorCodeException {
        entity.initPageParam();
        return operationLogDao.getPagedList(entity, entity.getCurPage(), entity.getPageSize());
    }

    @Override
    public List<OperationLog> getList(OperationLog entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public OperationLog get(OperationLog entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public int add(OperationLog entity) throws ErrorCodeException {
        return operationLogDao.insert(entity);
    }

    @Override
    public int modify(OperationLog entity) throws ErrorCodeException {
        return 0;
    }

    @Override
    public int delete(OperationLog entity) throws ErrorCodeException {
        return 0;
    }
}
