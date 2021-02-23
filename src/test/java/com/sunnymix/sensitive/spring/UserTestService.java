package com.sunnymix.sensitive.spring;

import com.sunnymix.sensitive.spring.aop.Sensitive;
import com.sunnymix.sensitive.spring.model.User;

public class UserTestService {

    @Sensitive
    public User maskOne(User user) {
        return user;
    }

}
