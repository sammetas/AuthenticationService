package com.auth.AuthenticationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JWTTokenGenerationFilter extends UsernamePasswordAuthenticationFilter {
   private  AuthenticationManager authenticationManager;

    public JWTTokenGenerationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserNameAndPasswordStore userNameAndPasswordStore=
                    new ObjectMapper().readValue(request.getInputStream(),UserNameAndPasswordStore.class);

            Authentication authentication= new UsernamePasswordAuthenticationToken(userNameAndPasswordStore.getUserName(),
                    userNameAndPasswordStore.getPassword());

        return  authentication;

        } catch (IOException e) {
          throw  new RuntimeException(e);
        }



    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       // System.out.println("jwtconfig::"+" "+jwtConfig.getKeyexpirydays().intValue());
        String key="QWERTUIOPLGJDHSLKJGHAIUHUHASKJBNVAJFSNVKJNBSIUGBHFSIBGSLKFDBVSMNVAISHEIURYWEIURMSFNVIAURJBJB";
        String token= Jwts.builder()
                .setSubject(authResult.getName())
              //  .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                //.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getKeyexpirydays().intValue())))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                //   .setId(UUID.randomUUID().toString())
                .compact();

        System.out.println("token"+token);
        //super.successfulAuthentication(request, response, chain, authResult);
      //  response.addHeader(jwtConfig.getAuthorization(),jwtConfig.getPrefix()+token);
        response.addHeader("Authorization","Bearer "+token);
        //chain.doFilter(request,response);
    }
}
