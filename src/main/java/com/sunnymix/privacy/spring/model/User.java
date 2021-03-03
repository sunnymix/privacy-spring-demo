package com.sunnymix.privacy.spring.model;

import com.sunnymix.privacy.PrivacyType;
import com.sunnymix.privacy.integrate.annotation.Privacy;
import com.sunnymix.privacy.integrate.annotation.PrivacyField;
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

    @PrivacyField(privacyType = PrivacyType.NAME)
    private String name;

    @PrivacyField(privacyType = PrivacyType.PHONE)
    private String phone;

    @PrivacyField(privacyType = PrivacyType.PHONE)
    private List<String> phoneList = new ArrayList<>();

    private List<User> friendList = new ArrayList<>();

}
