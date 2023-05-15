package com.leonteqsecurity.testSecurity.controllers;

import com.leonteqsecurity.testSecurity.config.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

//    private static final Logger LOG = (Logger) LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        System.out.println(authentication);
//        LOG.debug("Token request for user: '{}'", authentication);
        String token = tokenService.generateToken(authentication);
//        LOG.debug("Token: {}", token);
        return token;
    }

}