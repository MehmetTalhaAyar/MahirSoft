package com.mahirsoft.webservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.concretes.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

@Service
public class AppUserDetailsService implements UserDetailsService {

    UserAuthenticationService userAuthenticationService;



    public AppUserDetailsService(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        UserAuthentication inDB = userAuthenticationService.findByEmail(email);

        if(inDB == null){
            throw new UsernameNotFoundException(email + " is not found");
        }


        return new DefaultUser(inDB);

    }
    
}
