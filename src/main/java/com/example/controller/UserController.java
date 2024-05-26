package com.example.controller;

import com.example.request.SignInRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("singin")
    public ResponseEntity<?> authenticateUSer(@RequestBody SignInRequest signInRequest){
        return null;
    }
}
