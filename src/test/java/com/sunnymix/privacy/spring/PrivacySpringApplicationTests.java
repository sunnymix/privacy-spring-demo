package com.sunnymix.privacy.spring;

import com.sunnymix.privacy.spring.controller.UserController;
import com.sunnymix.privacy.spring.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class PrivacySpringApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
    }

    String url(String path) {
        return String.format("http://localhost:%s%s", port, path);
    }

    @Test
    void getUser() {
        assertThat(this.restTemplate.getForObject(url("/user"), String.class))
                .contains("{\"name\":\"S**\",\"phone\":\"123****1111\",\"phoneList\":[\"123****1112\",\"123****1113\"],\"friendList\":[{\"name\":\"T**\",\"phone\":\"123****1114\",\"phoneList\":[\"123****1115\"],\"friendList\":[]}]}");
    }

    @Autowired
    UserTestService userTestService;

    @Test
    void newUser() {
        User user = new User();
        user.setName("Sunny");
        user.setPhone("12300001111");
        user.setPhoneList(Arrays.asList("12300001111", "12300001112"));

        User maskUser = userTestService.maskOne(user);

        User expectMaskUser = new User();
        expectMaskUser.setName("S**");
        expectMaskUser.setPhone("123****1111");
        expectMaskUser.setPhoneList(Arrays.asList("123****1111", "123****1112"));

        assertThat(maskUser).isEqualTo(expectMaskUser);
    }

}
