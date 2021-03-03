package com.sunnymix.privacy.integrate.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Privacy {
}
