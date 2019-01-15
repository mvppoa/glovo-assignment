package com.glovoapp.backender.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RootServiceImpl implements RootService {

    private final String welcomeMessage;

    public RootServiceImpl(@Value("${backender.welcome_message}") String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Override
    public String getWelcomeMessage() {
        return this.welcomeMessage;
    }
}
