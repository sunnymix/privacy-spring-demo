package com.sunnymix.privacy.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sunnymix"})
public class PrivacySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivacySpringApplication.class, args);
    }

}
