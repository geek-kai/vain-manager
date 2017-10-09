package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.dao.RoleDao;
import com.vain.manager.entity.Role;
import com.vain.manager.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vain
 * @date 2017/10/9 20:01
 * @description 角色管理具体service类
 */
@Service
public class RoleServiceImpl extends AbstractBaseService implements IRoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageList<Role> getPagedList(Role entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public List<Role> getList(Role entity) throws ErrorCodeException {
        return roleDao.getList(entity);
    }

    @Override
    public Role get(Role entity) throws ErrorCodeException {
        return roleDao.get(entity);
    }

    @Override
    public void add(Role entity) throws ErrorCodeException {
        roleDao.insert(entity);
    }

    @Override
    public void modify(Role entity) throws ErrorCodeException {
        roleDao.update(entity);
    }

    @Override
    public void delete(Role entity) throws ErrorCodeException {
        roleDao.delete(entity);
    }
}
