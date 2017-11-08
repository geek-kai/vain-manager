package com.vain.manager.constant;

/**
 * @author vain
 * @description: 系统常量
 * @date 2017/8/31 11:50
 */
public class SysConstants {

    /**
     * 系统状态码
     *
     * @author vain
     */
    public static final class Code {

        /**
         * 通用成功
         */
        public static final int SUCCESS_CODE = 200;

        public static final String SUCCESS_MSG = "请求成功";

        /**
         * 当前已经是最新数据
         */
        public static final int NOT_MODIFIED_CODE = 304;
        public static final String NOT_MODIFIED_MSG = "当前已经是最新数据";

        /**
         * token已失效 / session已失效
         */
        public static final int TOKEN_INVALID_CODE = 401;
        public static final String TOKEN_INVALID_MSG = "token已失效";

        /**
         * refreshToken已失效
         */
        public static final int REFRESH_TOKEN_INVALID_CODE = -401;

        public static final String REFRESH_TOKEN_INVALID_MSG = "refreshToken已失效";

        /**
         * 无权操作
         */
        public static final int FORBIDDEN_CODE = 403;
        public static final String FORBIDDEN_MSG = "无权操作";

        /**
         * 请求的数据不存在
         */
        public static final int NOT_FOUND_CODE = 404;
        public static final String NOT_FOUND_MSG = "请求的数据不存在";

        /**
         * 已是最后一页
         */
        public static final int NO_MORE_DATA = 405;
        public static final String NO_MORE_DATA_MSG = "已到最后一页";


        /**
         * 500
         */
        public static final int INTERNAL_SERVER_ERROR_CODE = 500;
        public static final String INTERNAL_SERVER_ERROR_MSG = "服务异常";

        // 通用失败错误码前缀10
        /**
         * 通用失败
         */
        public static final int FAIL_CODE = 1000;
        public static final String FAIL_MSG = "请求失败";

        /**
         * 参数错误
         */
        public static final int PARAM_ERROR_CODE = 1001;
        public static final String PARAM_ERROR_MSG = "参数错误";

        /**
         * 数据重复操作（创建或修改）
         */
        public static final int OPER_REPEAT_CODE = 1002;
        public static final String OPER_REPEAT_MSG = "数据重复操作";

        /**
         * 数据已经存在
         */
        public static final int DATA_ALREADY_EXISTS_CODE = 1003;
        public static final String DATA_ALREADY_EXISTS_MSG = "数据已经存在";

        /**
         * 请求三方服务网络失败
         */
        public static final int THREE_PARTY_SERVICE_REQUEST_FAILURE_CODE = 1004;
        public static final String THREE_PARTY_SERVICE_REQUEST_FAILURE_MSG = "请求三方服务网络失败";

        /**
         * 有关联数据不能删除
         */
        public static final int DATA_ALREADY_RELATION_CODE = 1005;
        public static final String DATA_ALREADY_RELATION_MSG = "有关联数据不能删除";

        /**
         * 操作失败
         */
        public static final int DATA_MODIFY_EORROR_CODE = 1006;
        public static final String DATA_MODIFY_EORROR_MSG = "操作失败";

        //账号错误码 11
        /**
         * 验证码错误或已过期
         */
        public static final int VERIFY_CODE_ERROR_CODE = 1103;
        public static final String VERIFY_CODE_ERROR_MSG = "验证码错误或已过期";

        /**
         * 账号或密码错误
         */
        public static final int ACCOUNT_OR_PASSWORD_ERROR_CODE = 1104;

        public static final String ACCOUNT_OR_PASSWORD_ERROR_MSG = "账号或密码错误";

        /**
         * 用户锁定状态
         */
        public static final int ACCOUNT_IS_LOCKED_CODE = 1105;
        public static final String ACCOUNT_IS_LOCKED_MSG = "账户已锁定";

        /**
         * 用户名已经存在
         */
        public static final int ACCOUNT_IS_EXISTS_CODE = 1106;
        public static final String ACCOUNT_IS_EXISTS_MSG = "用户名已经存在";

        /**
         * 旧密码错误
         */
        public static final int OLD_PASSWORD_ERROR_CODE = 1107;
        public static final String OLD_PASSWORD_ERROR_MSG = "旧密码错误";

        /**
         * 账号信息不存在
         */
        public static final int ACCOUNT_IS_NOT_EXISTS_CODE = 1108;
        public static final String ACCOUNT_IS_NOT_EXISTS_MSG = "输入的账号信息不存在";


        /**
         * 只能修改个人信息
         */
        public static final int ONLY_PERSON_INFO_CODE = 1109;
        public static final String ONLY_PERSON_INFO_MSG = "只能修改个人信息";

        /**
         * 定时表达式错误
         */
        public static final int TASK_CRON_EXPRESSION_ERROR_CODE = 1201;
        public static final String TASK_CRON_EXPRESSION_ERROR_MSG = "定时表达式错误";

    }

    /**
     * 通用返回字段
     */
    public static final class JSONField {
        public static final String FIELD_CODE = "code";
        public static final String FIELD_MSG = "msg";
    }

    /**
     * 账户常量
     */
    public static final class AccountConstant {
        /**
         * 账户锁定状态 0- 未锁定 1- 已锁定
         */
        public static final int STATUS_UN_LOCKED = 0;
        public static final int STATUS_LOCKED = 1;

        /**
         * 账户类型 1：超级管理员 2：超级管理员赋予权限的管理组用户
         */
        public static final int ACCOUNT_TYPE_SUPERADMIN = 1;
        public static final int ACCOUNT_TYPE_ADMIN = 2;
    }

    /**
     * 删除标识位 0- 正常 1- 已删除
     */
    public static final class DeletedStatus {
        public static final int STATUS_UN_DELETED = 0;
        public static final int STATUS_DELETED = 1;
    }

    /**
     * 菜单相关 菜单初始化使用
     */
    public static final class MenuConstant {
        /**
         * 父菜单ID的常量
         */
        public static final long PARENT_ID_OF_NO_PARENT = 0;
        /**
         * 菜单类型的常量1：目录 2：菜单 3：操作
         */
        public static final int TYPE_DIR = 1;
        public static final int TYPE_MENU = 2;
        public static final int TYPE_OPERATE = 3;
    }

    /**
     * 角色相关
     */
    public static final class RoleConstant {

        public static final String SUPER_ADMINISTRATOR = "SUPER_ADMINISTRATOR";
    }

    /**
     * referer允许的地址
     */
    public static final String RefererConstant = "manager";

    public enum ENUMTASK {
        ISRUN(1, "运行"), NOTRUN(0, "没运行"), ISCONCURRENT(1, "并发"), NOTCONCURRENT(0, "禁止并发");
        private int state;
        private String name;

        private ENUMTASK(int state, String name) {
            this.state = state;
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public String getName() {
            return name;
        }
    }

}
