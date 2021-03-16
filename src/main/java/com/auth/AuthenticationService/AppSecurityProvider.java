package com.auth.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityProvider extends WebSecurityConfigurerAdapter {




     private PasswordEncoder passwordEncoder;
  //   private  final JWTSecret jwtSecret;
  //   private  final JWTConfig jwtConfig;

     @Autowired
    public AppSecurityProvider(PasswordEncoder passwordEncoder/*, JWTSecret jwtSecret, JWTConfig jwtConfig*/) {
        this.passwordEncoder = passwordEncoder;
       // this.jwtSecret = jwtSecret;
     //   this.jwtConfig = jwtConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JWTTokenGenerationFilter(authenticationManager()))
            .addFilterAfter(new JWTTokenVerifier(),JWTTokenGenerationFilter.class)
            .authorizeRequests()
            .antMatchers("/","index","/css").permitAll()
            .antMatchers("/api/**").hasRole("STUDENT")

            .anyRequest()
            .authenticated();



        //super.configure(http);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

   Set<SimpleGrantedAuthority> auths=new HashSet<>();
        auths.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        UserDetails samm= User.builder()
                    .username("samm")
                    .password(passwordEncoder.encode("samm"))
                    .roles("STUDENT")//.authorities(auths)
                    .build();

        UserDetails ramm= User.builder()
                          .username("ramm")
                          .password(passwordEncoder.encode("ramm"))

                          .roles("STUDENT")//.authorities(auths)
                          .build();

        return new InMemoryUserDetailsManager(samm,ramm);

    }
}
