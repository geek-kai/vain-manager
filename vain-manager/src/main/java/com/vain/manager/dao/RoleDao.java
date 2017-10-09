package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vain
 * @date 2017/10/9 19:43
 * @description 角色dao类
 */
@Repository
public class RoleDao extends AbstractBaseDao<Role> {

    @Override
    public PageList<Role> getPagedList(Role entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<Role> getList(Role entity) {
        return sqlSession.selectList("com.vain.manager.entity.Role.getList", entity);
    }

    @Override
    public Role get(Role entity) {
        return sqlSession.selectOne("com.vain.manager.entity.Role.get", entity);
    }

    @Override
    public int insert(Role entity) {
        return sqlSession.insert("com.vain.manager.entity.Role.insert", entity);
    }

    @Override
    public int update(Role entity) {
        return sqlSession.update("com.vain.manager.entity.Role.update", entity);
    }

    @Override
    public int delete(Role entity) {
        entity.setDeleted(SysConstants.DeletedStatus.STATUS_DELETED);
        return update(entity);
    }
}
