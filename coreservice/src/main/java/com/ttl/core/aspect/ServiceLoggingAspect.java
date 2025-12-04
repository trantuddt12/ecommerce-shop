package com.ttl.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    @Before("execution(* com.ttl..service..*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Service {}.{} with params: {}", className, methodName, args);
    }

    @AfterReturning(
            pointcut = "execution(* com.ttl..service..*(..))",
            returning = "result"
    )
    public void logAfterServiceMethod(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if (result != null) {
            log.info("Service {}.{} error {}", className, methodName, result);
        } else {
            log.info("Service {}.{} exec success", className, methodName);
        }
    }
}