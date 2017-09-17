package com.vain.manager.common.exception;

/**
 * @description:  处理业务逻辑时 主动抛出的异常
 * @author  vain
 * @date 2017/8/31 11:47
 */
@SuppressWarnings("serial")
public class ErrorRCodeException extends Exception {

    /**
     *
     */
    private int code;

    private String msg;

    public ErrorRCodeException(int code, String msg) {
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
