package com.mahirsoft.webservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private TokenFilter tokenFilter;

    public SecurityConfiguration(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authentication) ->
            authentication.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/auth/**")).anonymous()
            .requestMatchers(AntPathRequestMatcher.antMatcher( "/assets/**")).anonymous()
            .anyRequest().authenticated()
            // .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/userauthentication/add"))
            //     .authenticated().anyRequest().permitAll()
        );

        http.httpBasic(Customizer.withDefaults());

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf((csrf)->csrf.disable());

        http.headers((headers)-> headers.disable());

        return http.build();
    }

    


    
}
