package com.example.usermanager.config;

import com.example.usermanager.security.jwt.JwtConfigurer;
import com.example.usermanager.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((requests) -> requests
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/users/{id}").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/users/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers(HttpMethod.PUT,"/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .headers().frameOptions().disable();
        return httpSecurity.build();
    }
}
