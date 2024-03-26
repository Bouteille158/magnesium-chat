package com.sodium.api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired(required = false)
    BuildProperties buildProperties;

    @RequestMapping("/")
    public String defaultRoute() {
        return "Bienvenue sur mon API!";
    }

    @RequestMapping("/api")
    public ResponseEntity<Map<String, Object>> apiRoute() {
        String version = buildProperties != null ? buildProperties.getVersion() : "1.0.0dev";
        String name = buildProperties != null ? buildProperties.getName() : "Sodium API";
        Date date = new Date();

        Map<String, Object> apiDetails = new HashMap<>();
        apiDetails.put("version", version);
        apiDetails.put("name", name);
        apiDetails.put("date", date.toString());
        apiDetails.put("description", "API REST Sodium pour l'application de chat en temps réel Magnesium Chat,");

        return ResponseEntity.ok(apiDetails);
    }

}