package com.mahirsoft.webservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.concretes.UserService;
import com.mahirsoft.webservice.Entities.Models.User;

@Service
public class AppUserDetailsService implements UserDetailsService {

    UserService userAuthenticationService;



    public AppUserDetailsService(UserService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User inDB = userAuthenticationService.findByEmail(email);

        if(inDB == null){
            throw new UsernameNotFoundException(email + " is not found");
        }


        return new DefaultUser(inDB);

    }
    
}
