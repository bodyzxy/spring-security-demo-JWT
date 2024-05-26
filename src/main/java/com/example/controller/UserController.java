package com.example.controller;

import com.example.request.SignInRequest;
import com.example.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
