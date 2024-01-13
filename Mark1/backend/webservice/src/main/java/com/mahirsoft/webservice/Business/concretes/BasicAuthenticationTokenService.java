package com.mahirsoft.webservice.Business.concretes;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;

@Service
public class BasicAuthenticationTokenService implements TokenService {

   
    @Override
    public Token createToken(PostUserAuthenticationRequest userAuthentication) {


        String emailColonPassword = userAuthentication.getEmail() + ":" + userAuthentication.getPassword();

        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());

        return new Token("Basic",token);
    }

    
}
