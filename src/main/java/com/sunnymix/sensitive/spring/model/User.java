package com.sunnymix.sensitive.spring.model;

import com.sunnymix.sensitive.spring.aop.SensitiveField;
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

    @SensitiveField
    private String phone;

    @SensitiveField
    private List<String> phoneList = new ArrayList<>();

    private List<User> friendList = new ArrayList<>();

}
