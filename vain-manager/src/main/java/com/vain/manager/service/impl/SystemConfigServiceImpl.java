package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.dao.SystemConfigDao;
import com.vain.manager.entity.SystemConfig;
import com.vain.manager.service.ISystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vain
 * @date 2017/10/14 21:51
 * @description 系统配置接口
 */
@Service
public class SystemConfigServiceImpl extends AbstractBaseService implements ISystemConfigService {

    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    public PageList<SystemConfig> getPagedList(SystemConfig entity) throws ErrorCodeException {
        entity.initPageParam();
        return systemConfigDao.getPagedList(entity, entity.getCurPage(), entity.getPageSize());
    }

    @Override
    public List<SystemConfig> getList(SystemConfig entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public SystemConfig get(SystemConfig entity) throws ErrorCodeException {
        return systemConfigDao.get(entity);
    }

    @Override
    public int add(SystemConfig entity) throws ErrorCodeException {
        return 0;
    }

    @Override
    public int modify(SystemConfig entity) throws ErrorCodeException {
        return systemConfigDao.update(entity);
    }

    @Override
    public int delete(SystemConfig entity) throws ErrorCodeException {
        return 0;
    }
}
