package com.sunnymix.privacy.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrivacyAspect {

    @Around("@annotation(com.sunnymix.privacy.spring.aop.Privacy)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object resObj = proceedingJoinPoint.proceed();
        return PrivacyMask.maskAny(resObj, false);
    }

}
