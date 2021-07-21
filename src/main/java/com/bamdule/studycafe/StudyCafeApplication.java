package com.bamdule.studycafe;

import com.bamdule.studycafe.security.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
@EnableScheduling
public class StudyCafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCafeApplication.class, args);
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }
}
