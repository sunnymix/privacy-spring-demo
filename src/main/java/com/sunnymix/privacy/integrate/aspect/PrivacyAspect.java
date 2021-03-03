package com.sunnymix.privacy.integrate.aspect;

import com.sunnymix.privacy.core.PrivacyFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrivacyAspect {

    @Pointcut("@annotation(com.sunnymix.privacy.integrate.annotation.Privacy)")
    private void privacyMethod() {
    }

    @Around("privacyMethod()")
    public Object callAtPrivacyMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object resObj = proceedingJoinPoint.proceed();
        return PrivacyFacade.amend(resObj);
    }

}
