package com.sunnymix.privacy.spring;

import com.sunnymix.privacy.aop.Privacy;
import com.sunnymix.privacy.spring.model.User;

public class UserTestService {

    @Privacy
    public User maskOne(User user) {
        return user;
    }

}
