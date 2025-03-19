package com.mahirsoft.webservice.Business.concretes;

import java.util.Base64;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.DataAccess.TokenRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;




@Service
@ConditionalOnProperty(name = "mahirsoft.token-type", havingValue = "basic")
public class BasicAuthenticationTokenService implements TokenService {

    private UserAuthenticationRepository userAuthenticationRepository;

    private PasswordEncoder passwordEncoder;

    private TokenRepository tokenRepository;

  

    public BasicAuthenticationTokenService(UserAuthenticationRepository userAuthenticationRepository,
            PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token createToken(PostUserAuthenticationRequest userAuthentication) {


        String emailColonPassword = userAuthentication.getEmail() + ":" + userAuthentication.getPassword();

        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());

        return tokenRepository.save(new Token("Basic",token));
    }

    @Override
    public UserAuthentication verifyToken(String tokenWithPrefix) {
        
        if(tokenWithPrefix == null) return null;

        var base64Encoded = tokenWithPrefix.split(" ")[1];
        var decoded = new String(Base64.getDecoder().decode(base64Encoded));

        var credentials = decoded.split(":");
        var email = credentials[0];
        var password = credentials[1];

        var user = userAuthenticationRepository.findByEmail(email);

        if(!user.isPresent()) return null;

        if(!passwordEncoder.matches(password, user.get().getPassword())) return null;

        return user.get();
    }

    
}
