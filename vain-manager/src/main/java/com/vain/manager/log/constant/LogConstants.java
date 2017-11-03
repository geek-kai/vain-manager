package com.vain.manager.log.constant;

/**
 * @author vain
 * @date： 2017/11/3 11:54
 * @description： 日志操作type
 */
public class LogConstants {

    /**
     * 操作类型
     */
    public static class OperationLogType {
        /**
         * 新增
         */
        public static final int OPERATION_ADD = 1;

        /**
         * 修改
         */
        public static final int OPERATION_UPDATE = 2;

        /**
         * 删除
         */
        public static final int OPERATION_DELETE = 3;

        /**
         * 启用
         */
        public static final int OPERATION_ENABLE = 4;

        /**
         * 禁用
         */
        public static final int OPERATION_DISABLE = 5;

        /**
         * 登录
         */
        public static final int OPERATION_LOGIN = 6;
    }


}
