package com.bamdule.studycafe;

import com.bamdule.studycafe.security.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
@EnableScheduling
public class StudyCafeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StudyCafeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this.getClass());
    }


    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }
}
