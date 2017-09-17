package com.vain.manager.common.service;


import com.vain.manager.common.exception.ErrorRCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @description:  sevice类的抽象类
 * @author  vain
 * @date 2017/8/31 11:49
 */
public abstract class AbstractBaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * throwErrorRCodeException:对于错误的返回码，需要调用此方法，抛出异常给上层处理
     * 
     * @param code
     * @throws ErrorRCodeException
     */
    protected void throwErrorRCodeException(int code,String msg) throws ErrorRCodeException {
        throw new ErrorRCodeException(code,msg);
    }

}
