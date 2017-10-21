package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vain
 * @date： 2017/10/21 18:05
 * @description： t_user_role 操作dao类
 */
@Repository
public class UserRoleDao extends AbstractBaseDao<UserRole> {
    @Override
    public PageList<UserRole> getPagedList(UserRole entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<UserRole> getList(UserRole entity) {
        return null;
    }

    @Override
    public UserRole get(UserRole entity) {
        return this.sqlSession.selectOne("com.vain.manager.entity.UserRole.get", entity);
    }

    @Override
    public int insert(UserRole entity) {
        return this.sqlSession.insert("com.vain.manager.entity.UserRole.insert", entity);
    }

    @Override
    public int update(UserRole entity) {
        return this.sqlSession.update("com.vain.manager.entity.UserRole.update", entity);
    }

    @Override
    public int delete(UserRole entity) {
        return this.sqlSession.delete("com.vain.manager.entity.UserRole.delete", entity);
    }
}
