package com.sunnymix.privacy.spring;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sunny
 * @since 2021-03-01
 */
@TestConfiguration
@Configuration
@ComponentScan({"com.sunnymix"})
public class TestConfig {

    @Bean
    public UserTestService userTestService() {
        return new UserTestService();
    }

}
