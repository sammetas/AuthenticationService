package com.auth.AuthenticationService;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JWTSecret {/*


    private final JWTConfig jwtConfig;

    @Autowired
    public JWTSecret(JWTConfig jwtConfig) {
        this.jwtConfig=jwtConfig;

    }


    @Bean
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getKey().getBytes());
    }*/
}
