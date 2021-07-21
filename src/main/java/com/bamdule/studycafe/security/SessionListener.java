package com.bamdule.studycafe.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author MW
 */
public class SessionListener implements HttpSessionListener {

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
