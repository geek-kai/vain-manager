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
     * 操作用户Id
     */
    private Long userId;

    /**
     * 操作数据id
     */
    private String operationData;

    /**
     * 操作ip
     */
    private String operationIP;

    /**
     * 操作类
     */
    private String className;


    /**
     * 操作方法
     */
    private String methodName;

    /**
     * 异常信息
     */
    private String exceptionMessage;

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

    public String getOperationData() {
        return operationData;
    }

    public void setOperationData(String operationData) {
        this.operationData = operationData;
    }

    public String getOperationIP() {
        return operationIP;
    }

    public void setOperationIP(String operationIP) {
        this.operationIP = operationIP;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
