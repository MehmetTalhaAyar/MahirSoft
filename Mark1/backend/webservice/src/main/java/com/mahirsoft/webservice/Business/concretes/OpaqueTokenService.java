


package com.mahirsoft.webservice.Business.concretes;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.DataAccess.TokenRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;


@Service
@ConditionalOnProperty(name = "mahirsoft.token-type", havingValue = "opaque")
public class OpaqueTokenService implements TokenService {

    private UserAuthenticationRepository userAuthenticationRepository;

    private TokenRepository tokenRepository;

    public OpaqueTokenService(UserAuthenticationRepository userAuthenticationRepository,
            TokenRepository tokenRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token createToken(PostUserAuthenticationRequest userAuthentication) {
        

        var user = userAuthenticationRepository.findByEmail(userAuthentication.getEmail()).orElseThrow(()-> new ResourceNotFoundException());

        var tokenString = UUID.randomUUID().toString();

        Token token = new Token();
        token.setUser(user);
        token.setPrefix("Opaque");
        token.setToken(tokenString);

        return tokenRepository.save(token);
    }


    // burada değerler null dönülüyor hata fırlatılması filter içerisinde gerçekleşiyor.
    @Override
    public UserAuthentication verifyToken(String tokenWithPrefix) {
        
        if(tokenWithPrefix == null) return null;

        String tokenContent = tokenWithPrefix.split(" ")[1];

        var token = tokenRepository.findByToken(tokenContent);

        if(!token.isPresent()) return null;

        return token.get().getUser();
    }

    
}