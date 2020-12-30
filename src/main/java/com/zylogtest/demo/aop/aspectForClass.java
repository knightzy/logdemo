package com.zylogtest.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(-1)
public class aspectForClass {

    //将标有注解的类作为切入点
    @Pointcut("@within(com.zylogtest.demo.aop.domeAspect)")
    public void demoLog() {}

    //目标方法之前切入，切入点表达式
    @Before("demoLog()")
    public void logBefore(JoinPoint joinPoint) {
        log.info( "Order -1 for Class will go");
    }

    //目标方法之后切入，切入点表达式
    @After("demoLog()")
    public void logAfter(JoinPoint joinPoint){
        log.info("Order -1 for Class have gone");
    }

    //目标方法之后切入，同时在After之后，切入点表达式
    @AfterReturning(value="demoLog()",returning = "res")
    public void logReturning(JoinPoint joinPoint,Object res){
        log.info("Order -1 for Class have returnned for " + res.toString());
    }

    @Around("demoLog()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        Long startTime = System.currentTimeMillis();

        Object result = null;
        Throwable exception = null;

        try {
            result = joinPoint.proceed();
            return result;

        } catch (Exception e) {
            result = new Exception(e.getMessage(),e.getCause());
            return result;

        } catch (Throwable throwable) {
            //error日志打印出堆栈,方便排查问题.
            log.error("UNEXPECTED ERROR: [INTERFACE : {}], [METHOD : {}], [ARGS : {}], EXCEPTION : ",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    joinPoint.getArgs(),
                    throwable);
            return  throwable;

        } finally {
            long timeSpend = System.currentTimeMillis() - startTime;
            if (exception != null) {
                log.error("[INTERFACE : {}], [METHOD : {}], [ARGS : {}], [EXCEPTION : {}], [SPEND TIME : {}]",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        joinPoint.getArgs(),
                        exception.getMessage(),
                        timeSpend);
            }
            if (result != null) {
                log.info("[INTERFACE : {}], [METHOD : {}], [ARGS : {}], [RESULT : {}], [SPEND TIME : {}]",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        joinPoint.getArgs(),
                        result.toString(),
                        timeSpend);
            }
        }
    }

}
