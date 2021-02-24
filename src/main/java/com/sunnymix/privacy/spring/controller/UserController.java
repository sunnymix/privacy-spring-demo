package com.sunnymix.privacy.spring.controller;

import com.sunnymix.privacy.spring.aop.Privacy;
import com.sunnymix.privacy.spring.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class UserController {

    @GetMapping("/user")
    @Privacy
    public User getUser() {
        User user = new User();
        user.setName("sunny");
        user.setPhone("12300001111");
        user.setPhoneList(Arrays.asList("12300001112", "12300001113"));

        User friend = new User();
        friend.setName("Tom");
        friend.setPhone("12300001114");
        friend.setPhoneList(Arrays.asList("12300001115"));

        user.setFriendList(Arrays.asList(friend));

        return user;
    }

}
