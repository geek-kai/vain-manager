package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;

/**
 * @author vain
 * @date： 2017/10/31 11:40
 * @description： 定时任务实体类 t_schedule_job
 */
public class ScheduleJob extends PagedEntity {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组
     */
    private String jobGroup;

    /**
     * 任务状态
     */
    private Integer jobStatus;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务执行类（全路径）
     */
    private String jobClass;

    /**
     * 任务执行类执行方法
     */
    private String jobMethod;

    /**
     * 是否同步
     */
    private Integer isConcurrent;

    private Timestamp createTime;

    private Timestamp modifyTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Integer isConcurrent) {
        this.isConcurrent = isConcurrent;
    }
}

