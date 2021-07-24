package com.bamdule.studycafe.security;

import com.bamdule.studycafe.config.PropertyConfig;
import com.bamdule.studycafe.config.StudyCafeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private StudyCafeConfig studyCafeConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("[security] Login Success");
        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();

        studyCafeConfig.init();
        propertyConfig.setUseSeatSchedule(true);

        httpServletResponse.sendRedirect("/studycafe");
    }
}
