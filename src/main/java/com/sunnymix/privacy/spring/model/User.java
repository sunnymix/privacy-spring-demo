package com.sunnymix.privacy.spring.model;

import com.sunnymix.privacy.PrivacyType;
import com.sunnymix.privacy.aop.Privacy;
import com.sunnymix.privacy.aop.PrivacyField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Privacy
public class User {

    private String name;

    @PrivacyField(privacyType = PrivacyType.PHONE)
    private String phone;

    @PrivacyField(privacyType = PrivacyType.PHONE)
    private List<String> phoneList = new ArrayList<>();

    private List<User> friendList = new ArrayList<>();

}
