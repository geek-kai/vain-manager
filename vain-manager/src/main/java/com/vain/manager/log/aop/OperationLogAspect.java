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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author vain
 * @date： 2017/11/3 11:23
 * @description： 如出现aop拦截方法执行2次 检查bean的生成是否重复
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
                    //可以通过foreach来遍历 存储请求的所有参数 这里请求的实体参数设置为第一个 所以只将第一个转换为String存
                    Map<String, String> map = FieldsUtil.getFieldsByReflect(args[0]);
                    if (operationLog.isOnlyId()) {
                        log.setOperationData("{id:" + map.get("id") + "}");
                        logger.info(">>>>>>>>>拦截到的参数ID为:{}", map.get("id"));
                    } else {
                        Set<Map.Entry<String, String>> entries = map.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            if (null == next.getValue() || "".equals(next.getValue()) || "0".equals(next.getValue()) || "passwd".equals(next.getKey()) ||
                                    "createTime".equals(next.getKey()) || "modifyTime".equals(next.getKey()))
                                iterator.remove();
                        }
                        log.setOperationData(JSONObject.toJSONString(map));
                        logger.info(">>>>>>>>>拦截到的参数为:{}", map);
                    }
                    if (joinPoint.getArgs().length > 1) {
                        Object request = joinPoint.getArgs()[1];
                        if (request instanceof HttpServletRequest) {
                            HttpServletRequest req = (HttpServletRequest) request;
                            log.setOperationIP(req.getRemoteAddr());
                        }
                    }
                    log.setInfo(operationLog.info());
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
            String params = "";
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < 1; i++) {
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
