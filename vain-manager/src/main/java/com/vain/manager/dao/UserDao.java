package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @description: 用户信息操作dao
 * @author  vain
 * @date 2017/8/31 11:57
 */
@Repository
public class UserDao extends AbstractBaseDao<User> {
    /**
     * 获取分页数据
     *
     * @param entity   参数实体
     * @param curPage  当前页码
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public PageList<User> getPagedList(User entity, int curPage, int pageSize) {
        PageBounds pageBounds = new PageBounds(curPage, pageSize);
        return (PageList) this.sqlSession.selectList("com.vain.manager.entity.User.getPagedList", entity, pageBounds);
    }

    /**
     * 获取全部数据
     * @param entity
     * @return
     * @return
     */
    public List<User> getPagedList(User entity) {
        PageBounds pageBounds = new PageBounds();
        return this.sqlSession.selectList("com.vain.manager.entity.User.getPagedList", entity, pageBounds);
    }


    /**
     * 获取所有数据
     *
     * @param entity 参数实体
     * @return
     */
    @Override
    public List<User> getList(User entity) {
        return this.sqlSession.selectList("com.vain.manager.entity.User.getList", entity);
    }

    /**
     * 获取单条数据
     *
     * @param entity 参数实体
     * @return
     */
    @Override
    public User get(User entity) {
        return this.sqlSession.selectOne("com.vain.manager.entity.User.get", entity);
    }

    /**
     * 插入数据
     *
     * @param entity 参数实体
     * @return
     */
    @Override
    public int insert(User entity) {
        return this.sqlSession.insert("com.vain.manager.entity.User.insert", entity);
    }

    /**
     * 更新数据
     *
     * @param entity 参数实体
     * @return
     */
    @Override
    public int update(User entity) {
        return this.sqlSession.update("com.vain.manager.entity.User.update", entity);
    }

    /**
     * 删除数据
     *
     * @param entity 参数实体
     * @return
     */
    @Override
    public int delete(User entity) {
        return this.sqlSession.update("com.vain.manager.entity.User.delete", entity);
    }
}
