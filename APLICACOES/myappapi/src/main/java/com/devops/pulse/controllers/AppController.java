package com.devops.pulse.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Aplicando test ... ");
    }

    @RequestMapping("/api")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Map<String, String>> api() {
        HashMap<String, String> map = new HashMap<>();
        map.put("greeting", "Aplicando test ... ");
        return ResponseEntity.ok(map) ;
    }

}
