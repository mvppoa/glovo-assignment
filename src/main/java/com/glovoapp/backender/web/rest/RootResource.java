package com.glovoapp.backender.web.rest;

import com.glovoapp.backender.services.RootService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootResource {

    private final RootService rootService;

    public RootResource(RootService rootService) {
        this.rootService = rootService;
    }

    @GetMapping
    @ResponseBody
    public String getWelcomeMessage() {
        return rootService.getWelcomeMessage();
    }

}
