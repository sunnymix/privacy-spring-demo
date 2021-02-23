package com.sunnymix.sensitive.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SensitiveAspect {

    @Around("@annotation(com.sunnymix.sensitive.spring.aop.Sensitive)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object resObj = proceedingJoinPoint.proceed();
        return SensitiveMask.maskAny(resObj, false);
    }

}
