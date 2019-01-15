package com.glovoapp.backender.web.rest;

import com.glovoapp.backender.services.RootService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootApiResource implements RootApi {

    private final RootService rootService;

    public RootApiResource(RootService rootService) {
        this.rootService = rootService;
    }

    @Override
    public String getWelcomeMessage() {
        return rootService.getWelcomeMessage();
    }

}
