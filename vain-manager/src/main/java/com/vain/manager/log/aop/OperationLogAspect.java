package com.vain.manager.log.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vain.manager.log.FieldsUtil;
import com.vain.manager.log.OperationLog;
import com.vain.manager.log.service.IOperationLogService;
import com.vain.manager.shiro.session.UserSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author vain
 * @date： 2017/11/3 11:23
 * @description：
 */
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private IOperationLogService operationLogService;

    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    /**
     * 切点
     */
    @Pointcut("@annotation(com.vain.manager.log.OperationLog)")
    public void logPoint() {

    }

    /**
     * 前置通知
     */
    @Before("logPoint()")
    public void before(JoinPoint joinPoint) {
    }

    @AfterReturning(pointcut = "logPoint()")
    public void doAfter(JoinPoint joinPoint) {
        logServiceHandler(joinPoint, null);
    }

    /**
     * 切面操作
     *
     * @param joinPoint
     * @param e
     */
    @SuppressWarnings("all")
    private void logServiceHandler(JoinPoint joinPoint, Exception e) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            OperationLog operationLog = method.getAnnotation(OperationLog.class);
            if (operationLog != null) {
                try {
                    com.vain.manager.log.entity.OperationLog log = new com.vain.manager.log.entity.OperationLog();
                    // 拦截的方法参数
                    String classType = joinPoint.getTarget().getClass().getName();
                    Class<?> clazz = Class.forName(classType);
                    String clazzName = clazz.getName();
                    String methodName = joinPoint.getSignature().getName();
                    log.setClassName(clazzName);
                    log.setMethodName(methodName);
                    logger.info(">>>>>>>>>操作类：{},操作方法{}", clazzName, methodName);
                    Object[] args = joinPoint.getArgs();
                    for (Object obj : args) {
                        Map<String, String> map = FieldsUtil.getFieldsByReflect(obj);
                        Set<Map.Entry<String, String>> entries = map.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            if (null == next.getValue() || "".equals(next.getValue()) || "0".equals(next.getValue()))
                                iterator.remove();
                        }
                        if (method.equals("login"))
                            map.remove("passwd");//清除密码
                        log.setOperationData(JSONObject.toJSONString(map));
                        logger.info(">>>>>>>>>拦截到的注解参数为:{}", map);
                    }

                    log.setOperationType(operationLog.operationType());
                    log.setUserId(UserSession.getUserId()); //操作用户id
                    operationLogService.add(log);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    logger.info(">>>>>>>>>异常:{}", e1.getMessage());
                }
            }
        }
    }


    /**
     * 异常拦截
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        //请求方法的参数并序列化为JSON格式字符串
        com.vain.manager.log.entity.OperationLog log = new com.vain.manager.log.entity.OperationLog();
        try {
            String classType = joinPoint.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = joinPoint.getSignature().getName();
            log.setClassName(clazzName);
            log.setMethodName(methodName);
            log.setExceptionMessage(e.getMessage());
            log.setInfo(e.getClass().getName());
            String params = null;
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
                }
            }
            log.setOperationData(params);
            log.setUserId(UserSession.getUserId()); //操作用户id
            operationLogService.add(log);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        logger.info(">>>>>>>>>异常捕捉:{}", e.getMessage());
    }


}
