package com.bamdule.studycafe.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

//    @PostConstruct
//    public StudyCafeConfig studyCafeConfig() {
//        StudyCafeConfig studyCafeConfig = new StudyCafeConfig(1);
//        return studyCafeConfig;
//    }

}
