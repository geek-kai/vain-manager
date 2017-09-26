package com.vain.manager.common.entity;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author vain
 * @description: 响应分页list集合基类
 * @date 2017/8/31 11:46
 */
public class ResponseList<E> extends PageList<E> implements Serializable {

    private static final long serialVersionUID = 8720228324034779637L;

    /**
     * 数据的最后修改时间，用来判断数据是否有更新
     */
    private Timestamp lastModifyTime;

    public ResponseList() {
    }

    public ResponseList(Collection<? extends E> c) {
        super(c);
    }

    public ResponseList(Collection<? extends E> c, Timestamp lastModifyTime) {
        super(c);
        this.lastModifyTime = lastModifyTime;
    }

    public ResponseList(PageList<? extends E> pageList) {
        super(pageList, pageList.getPaginator());
    }

    public ResponseList(PageList<? extends E> pageList, Timestamp lastModifyTime) {
        super(pageList, pageList.getPaginator());
        this.lastModifyTime = lastModifyTime;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
