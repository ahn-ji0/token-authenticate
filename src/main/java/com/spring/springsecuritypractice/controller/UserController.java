package com.spring.springsecuritypractice.controller;

import com.spring.springsecuritypractice.domain.UserLoginRequest;
import com.spring.springsecuritypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
