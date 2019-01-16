package com.glovoapp.backender.services;

import com.glovoapp.backender.helper.OrderHelperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RootServiceImpl implements RootService {

    private static final Logger log = LoggerFactory.getLogger(OrderHelperImpl.class);

    private final String welcomeMessage;

    public RootServiceImpl(@Value("${backender.welcome_message}") String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Override
    public String getWelcomeMessage() {
        log.debug("Entered welcome method");
        return this.welcomeMessage;
    }
}
