package com.sunnymix.privacy.spring;

import com.sunnymix.privacy.core.annotation.Privacy;
import com.sunnymix.privacy.spring.model.User;

/**
 * @author Sunny
 * @since 2021-03-01
 */
public class UserTestService {

    @Privacy
    public User maskOne(User user) {
        return user;
    }

}
