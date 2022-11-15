package com.example.usermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                .antMatchers(HttpMethod.GET, "/user/inject").permitAll()
                .antMatchers(HttpMethod.GET, "/user").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/user/{id}").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/user/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/user/{id}/edit").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
