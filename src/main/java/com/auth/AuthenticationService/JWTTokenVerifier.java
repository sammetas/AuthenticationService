package com.auth.AuthenticationService;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTTokenVerifier  extends OncePerRequestFilter {

    public JWTTokenVerifier() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String requestAuth=httpServletRequest.getHeader("Authorization");

        if(Strings.isNullOrEmpty(requestAuth) || !requestAuth.startsWith("Bearer ")) {
             filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

            String token = requestAuth.replace("Bearer ", "");
        try {
            String key = "QWERTUIOPLGJDHSLKJGHAIUHUHASKJBNVAJFSNVKJNBSIUGBHFSIBGSLKFDBVSMNVAISHEIURYWEIURMSFNVIAURJBJB";

            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                    .parseClaimsJws(token);

            Claims body = claims.getBody();
            String userName = body.getSubject();

            var authorities = (List<Map<String, String>>) body.get("authorities");

           /* Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());*/
            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
            System.out.println("simpleGrantedAuthoritySet"+simpleGrantedAuthoritySet);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthoritySet);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (JwtException e){
            System.out.println(String.format("Token %s can't be trusted",token));
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
