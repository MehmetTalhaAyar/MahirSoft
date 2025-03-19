package com.mahirsoft.webservice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {


    private TokenService tokenService;

    private HandlerExceptionResolver exceptionResolver;

    public TokenFilter(TokenService tokenService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.tokenService = tokenService;
        this.exceptionResolver = exceptionResolver;
    }





    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        
        String tokenWithPrefix = getTokenWithPrefix(request);

        if(tokenWithPrefix != null){
            UserAuthentication user = tokenService.verifyToken(tokenWithPrefix);


            if(user != null){

                // exceptionResolver.resolveException(request, response, null, new DisabledException("User is Disabled"));
                // user exceptions

            }


            DefaultUser currentUser = new DefaultUser(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser, null,currentUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        
        // TODO Auto-generated method stub
        filterChain.doFilter(request, response);
    }
    




    private String getTokenWithPrefix(HttpServletRequest request){

        var tokenWithPrefix = request.getHeader("Authorization");
        var cookies = request.getCookies();

        if(cookies == null) return tokenWithPrefix;

        for(var cookie : cookies){
            if(!cookie.getName().equals("mahirsoft-token")) continue;
            
            if(cookie.getValue() == null || cookie.getValue().isEmpty()) continue;

            return "AnyPrefix " + cookie.getValue();
        }
        return tokenWithPrefix;
    }
}
