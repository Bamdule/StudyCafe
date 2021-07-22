package com.bamdule.studycafe.security;

import com.bamdule.studycafe.config.PropertyConfig;
import com.bamdule.studycafe.config.StudyCafeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private StudyCafeConfig studyCafeConfig;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("[security] Logout Success");
        propertyConfig.setUseSeatSchedule(false);
        studyCafeConfig.destroy();
    }
}
