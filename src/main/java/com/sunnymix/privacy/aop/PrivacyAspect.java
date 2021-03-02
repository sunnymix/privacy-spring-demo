package com.sunnymix.privacy.aop;

import com.sunnymix.privacy.exception.PrivacyException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrivacyAspect {

    @Pointcut("@annotation(com.sunnymix.privacy.aop.Privacy)")
    private void privacyMethod() {
    }

    @Around("privacyMethod()")
    public Object callAtPrivacyMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object resObj = proceedingJoinPoint.proceed();
        throw new PrivacyException("todo");
    }

}
