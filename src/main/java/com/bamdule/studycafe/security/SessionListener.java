package com.bamdule.studycafe.security;

import com.bamdule.studycafe.config.PropertyConfig;
import com.bamdule.studycafe.config.StudyCafeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author MW
 */
public class SessionListener implements HttpSessionListener {

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private StudyCafeConfig studyCafeConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int sessionTimeout = 60 * 60 * 24 * 7;

    @Override
    public void sessionCreated(HttpSessionEvent session) {
        session.getSession().setMaxInactiveInterval(sessionTimeout);
        logger.info("[MYTEST] sessionCreated");


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent session) {
        logger.info("[MYTEST] sessionDestroyed");

    }
}
