package com.vain.manager.log.entity;

import com.vain.manager.common.entity.PagedEntity;

/**
 * @author vain
 * @date： 2017/11/3 11:12
 * @description： 日志实体类
 */
public class OperationLog extends PagedEntity {
    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 用户类型
     */
    private Long userId;

    /**
     * 操作数据id
     */
    private Long operationId;

    /**
     * 操作信息
     */
    private String info;

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
