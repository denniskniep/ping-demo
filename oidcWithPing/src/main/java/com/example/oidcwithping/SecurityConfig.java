package com.example.oidcwithping;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/", "/saml2/service-provider-metadata/**", "/login/saml2/sso/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
        ;
    }
}