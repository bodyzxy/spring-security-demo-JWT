package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bodyzxy
 * @github https://github.com/bodyzxy
 * @date 2024/5/30 20:09
 */
@RestController
@RequestMapping("/api")
public class MessageController {
    @GetMapping("/message")
    public ResponseEntity<String> message() {
        return ResponseEntity.ok("Hello World");
    }
}
