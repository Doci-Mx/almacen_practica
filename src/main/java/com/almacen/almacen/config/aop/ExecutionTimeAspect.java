package com.almacen.almacen.config.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class ExecutionTimeAspect {

    @Around("@annotation(com.almacen.almacen.config.annotation.TimedExecution)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long duration = end - start;

        log.info("Método {} tardó {} ms",
                joinPoint.getSignature().toShortString(),
                duration);

        return result;
    }
}

