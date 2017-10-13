package com.vain.manager.common.entity;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.constant.SysConstants;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author vain
 * @description: resufull相应实体
 * @date 2017/8/31 11:42
 */
public class Response<T extends Entity> {

    /**
     * 响应码，参见SysConstants中Code常量类
     *
     * @see com.vain.manager.constant.SysConstants.Code
     */
    private Integer code;

    /**
     * 响应消息，参见SysConstants中Msg常量类
     *
     * @see com.vain.manager.constant.SysConstants.Code
     */
    private String msg;

    /**
     * 针对分页返回的记录总大小
     */
    private Integer totalSize;

    /**
     * 数据的最后修改时间，用来判断数据是否有更新
     */
    private Timestamp lastModifyTime;

    /**
     * 单条数据
     */
    private Object data;

    /**
     * 列表数据
     */
    private List<T> dataList;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Object getData() {
        return data;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setData(Object data) {
        this.data = data;
        if (data == null) {
            this.code = SysConstants.Code.NOT_FOUND_CODE;
            this.msg = SysConstants.Code.NOT_FOUND_MSG;
        } else {
            this.code = SysConstants.Code.SUCCESS_CODE;
            this.msg = SysConstants.Code.SUCCESS_MSG;
        }
        if (data instanceof Integer) {
            if ((int) data == 0) {  //数据库影响的行数为0 没修改数据
                this.code = SysConstants.Code.DATA_MODIFY_EORROR_CODE;
                this.msg = SysConstants.Code.DATA_MODIFY_EORROR_MSG;
            } else if ((int) data > 1) {
                this.code = SysConstants.Code.SUCCESS_CODE;
                this.msg = SysConstants.Code.SUCCESS_MSG;
            }
        }
    }

    /**
     * 抽取公用code 如果需要自己设置code 可在设置data之后再set code和msg
     *
     * @param dataList
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        if (dataList == null || dataList.isEmpty()) {
            this.code = SysConstants.Code.NOT_FOUND_CODE;
            this.msg = SysConstants.Code.NOT_FOUND_MSG;
        } else {
            this.code = SysConstants.Code.SUCCESS_CODE;
            this.msg = SysConstants.Code.SUCCESS_MSG;
        }
        if (dataList instanceof PageList) {
            this.totalSize = ((PageList) dataList).getPaginator().getTotalCount();
        }
    }

}
