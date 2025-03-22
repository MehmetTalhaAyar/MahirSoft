package com.mahirsoft.webservice.Business.concretes;

import java.util.Base64;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.DataAccess.TokenRepository;
import com.mahirsoft.webservice.DataAccess.UserRepository;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.PostUserRequest;




@Service
@ConditionalOnProperty(name = "mahirsoft.token-type", havingValue = "basic")
public class BasicAuthenticationTokenService implements TokenService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private TokenRepository tokenRepository;

  

    public BasicAuthenticationTokenService(UserRepository userRepository,
            PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token createToken(PostUserRequest postUserRequest) {

        User user = userRepository.findByEmail(postUserRequest.getEmail()).orElseThrow(()-> new UserNotFoundException());

        String emailColonPassword = postUserRequest.getEmail() + ":" + postUserRequest.getPassword();

        String tokenString = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());

        Token token = new Token();
        token.setPrefix("Basic");
        token.setToken(tokenString);
        token.setUser(user);
        


        return tokenRepository.save(token);
    }

    @Override
    public User verifyToken(String tokenWithPrefix) {
        
        if(tokenWithPrefix == null) return null;

        var base64Encoded = tokenWithPrefix.split(" ")[1];
        var decoded = new String(Base64.getDecoder().decode(base64Encoded));

        var credentials = decoded.split(":");
        var email = credentials[0];
        var password = credentials[1];

        var user = userRepository.findByEmail(email);

        if(!user.isPresent()) return null;

        if(!passwordEncoder.matches(password, user.get().getPassword())) return null;

        return user.get();
    }

    
}
