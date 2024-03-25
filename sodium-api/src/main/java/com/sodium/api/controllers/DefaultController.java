package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String defaultRoute() {
        return "Bienvenue sur mon API!";
    }

}