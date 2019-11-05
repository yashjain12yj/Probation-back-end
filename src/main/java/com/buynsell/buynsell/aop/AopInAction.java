package com.buynsell.buynsell.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.logging.Logger;

@Aspect
@Component
public class AopInAction {
    private static final Logger LOG = Logger.getLogger(AopInAction.class.getName());

    @Around("execution(* com.buynsell.buynsell.repository..*(..)) || execution(* com.buynsell.buynsell.controller..*(..)) || execution(* com.buynsell.buynsell.service..*(..))")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info(joinPoint.getTarget() + "." + joinPoint.getSignature().getName() + " Begin");
        Object retVal = joinPoint.proceed();
        LOG.info(joinPoint.getTarget() + "." + joinPoint.getSignature().getName() + " End");
        return retVal;
    }

    @Around("execution(* com.buynsell.buynsell.repository..*(..))")
    public Object performance(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        LOG.info(joinPoint.getTarget() + "." + joinPoint.getSignature().getName() + " -> executed in " + stopWatch.getTotalTimeMillis() + "ms");
        return retVal;
    }

    @AfterThrowing(pointcut = "execution(* com.buynsell.buynsell.*..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        LOG.warning("***Exception occurred in " + joinPoint.getSignature().toShortString() + " -> " + ex);
    }
}
