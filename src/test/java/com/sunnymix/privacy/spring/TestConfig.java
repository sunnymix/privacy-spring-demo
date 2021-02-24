package com.sunnymix.privacy.spring;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
@Configuration
public class TestConfig {

    @Bean
    public UserTestService userTestService() {
        return new UserTestService();
    }

}
