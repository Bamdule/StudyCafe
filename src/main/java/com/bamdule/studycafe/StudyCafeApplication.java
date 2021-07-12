package com.bamdule.studycafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudyCafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCafeApplication.class, args);
    }

}
