package com.vain.manager.common.exception;

import com.vain.manager.common.entity.Entity;
import com.vain.manager.common.entity.Response;
import com.vain.manager.constant.SysConstants;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author vain
 * @description: 捕获异常 返回相应的code和msg
 * @date 2017/8/31 11:48
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    // 定义全局异常处理，value属性可以过滤拦截条件，此处拦截所有的Exception
    @ExceptionHandler({Exception.class})
    public
    @ResponseBody
    Response<Entity> processControllerException(NativeWebRequest request, Exception e) {

        Response<Entity> response = new Response<>();

        if (e instanceof ErrorRCodeException) {
            response.setCode(((ErrorRCodeException) e).getCode());
            response.setMsg(((ErrorRCodeException) e).getMsg());
        } else if (e instanceof UnauthorizedException) {
            logger.error(e.getMessage(), e);
            response.setCode(SysConstants.Code.FORBIDDEN_CODE);
            response.setMsg(SysConstants.Code.FORBIDDEN_MSG);
        } else {
            logger.error(e.getMessage(), e);
            response.setCode(SysConstants.Code.INTERNAL_SERVER_ERROR_CODE);
            response.setMsg(SysConstants.Code.INTERNAL_SERVER_ERROR_MSG);
        }
        return response;
    }

}
