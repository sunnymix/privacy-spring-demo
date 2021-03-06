package com.sunnymix.privacy.spring.model;

import com.sunnymix.privacy.core.PrivacyType;
import com.sunnymix.privacy.core.annotation.PrivacyField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunny
 * @since 2021-03-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @PrivacyField(privacyType = PrivacyType.NAME)
    private String name;

    @PrivacyField(privacyType = PrivacyType.PHONE)
    private String phone;

    @PrivacyField(privacyType = PrivacyType.PHONE)
    private List<String> phoneList = new ArrayList<>();

    private List<User> friendList = new ArrayList<>();

}
