package com.vain.manager.common.exception;

/**
 * @author vain
 * @date 2017/8/31 11:47
 * @description: 处理业务逻辑时 主动抛出的异常
 * 通常情况下，如果在事务中抛出了未检查异常（继承自 RuntimeException 的异常），则默认将回滚事务。
 * 如果没有抛出任何异常，或者抛出了已检查异常，则仍然提交事务。这通常也是大多数开发者希望的处理方式，
 * 但是，我们可以根据需要人为控制事务在抛出某些未检查异常时任然提交事务，或者在抛出某些已检查异常时回滚事务。
 * 所以异常父类继承允许时异常 主动抛出异常 时候希望 回滚
 * 也可以继承Exception 在希望事务回滚的时候 在事务注解中加入rollbackFor 来回滚
 */
@SuppressWarnings("serial")
public class ErrorCodeException extends RuntimeException {

    /**
     *
     */
    private int code;

    private String msg;

    public ErrorCodeException(int code, String msg) {
        super("error code: " + code);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
