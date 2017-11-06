package com.vain.manager.log.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.security.Timestamp;
import java.util.List;

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

    /**
     * 用户名
     */
    private String userName;

    /**
     * 操作时间
     */
    private Timestamp operationTime;

    /**
     * 状态集合
     */
    private List<Integer> operationTypes;

    public List<Integer> getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(List<Integer> operationTypes) {
        this.operationTypes = operationTypes;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
