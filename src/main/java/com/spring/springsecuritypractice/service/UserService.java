package com.spring.springsecuritypractice.service;

import com.spring.springsecuritypractice.domain.User;
import com.spring.springsecuritypractice.repository.UserRepository;
import com.spring.springsecuritypractice.token.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Value("${jwt.token.key}")
    private String secretKey;
    private long expiredMs = 1000 * 60 * 60 ;

    public String login(String userName, String password){
        User user = userRepository.findByUserName(userName).orElseThrow( () -> new RuntimeException() );

        if(!password.equals(user.getPassword())){
            throw new RuntimeException();
        }

        return JwtTokenUtils.createToken(user.getUserName(),secretKey,expiredMs);
    }
}
