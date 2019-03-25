package com.java.studentApp.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.csrf().disable().antMatcher("/").authorizeRequests().antMatchers("/", "/index.html").permitAll()
            .anyRequest().authenticated().and().exceptionHandling()
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());

        /*
         * http.csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/api/**").permitAll().anyRequest()
         * .authenticated().and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
         */
    }
}
