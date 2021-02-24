package com.sunnymix.privacy.spring.model;

import com.sunnymix.privacy.spring.aop.PrivacyField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;

    @PrivacyField
    private String phone;

    @PrivacyField
    private List<String> phoneList = new ArrayList<>();

    private List<User> friendList = new ArrayList<>();

}
