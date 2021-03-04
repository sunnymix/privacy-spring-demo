package com.sunnymix.privacy.spring.controller;

import com.sunnymix.privacy.core.annotation.Privacy;
import com.sunnymix.privacy.spring.model.JsonResult;
import com.sunnymix.privacy.spring.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class UserController {

    @Privacy
    @GetMapping("/user")
    public JsonResult<User> getUser() {

        User tom = new User();
        tom.setName("Tom");
        tom.setPhone("12300001114");
        tom.setPhoneList(Arrays.asList("12300001115"));

        User jerry = new User();
        jerry.setName("Jerry");
        jerry.setPhone("12300001116");
        jerry.setPhoneList(Arrays.asList("12300001117"));

        User sunny = new User();
        sunny.setName("Sunny");
        sunny.setPhone("12300001111");
        sunny.setPhoneList(Arrays.asList("12300001112", "12300001113"));
        sunny.setFriendList(Arrays.asList(tom, jerry));

        return JsonResult.of(sunny);
    }

}
