


package com.mahirsoft.webservice.Business.concretes;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.DataAccess.TokenRepository;
import com.mahirsoft.webservice.DataAccess.UserRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.PostUserRequest;


@Service
@ConditionalOnProperty(name = "mahirsoft.token-type", havingValue = "opaque")
public class OpaqueTokenService implements TokenService {

    private UserRepository userRepository;

    private TokenRepository tokenRepository;

    
    public OpaqueTokenService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public Token createToken(PostUserRequest postUserRequest) {
        

        var user = userRepository.findByEmail(postUserRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());

        var tokenString = UUID.randomUUID().toString();

        Token token = new Token();
        token.setUser(user);
        token.setPrefix("Opaque");
        token.setToken(tokenString);

        return tokenRepository.save(token);
    }


    // burada değerler null dönülüyor hata fırlatılması filter içerisinde gerçekleşiyor.
    @Override
    public User verifyToken(String tokenWithPrefix) {
        
        if(tokenWithPrefix == null) return null;

        String tokenContent = tokenWithPrefix.split(" ")[1];

        var token = tokenRepository.findByToken(tokenContent);

        // token in varligi kontrol ediliyor
        if(!token.isPresent()) return null;

        // token süresi geçmiş mi diye kontrol yapiliyor
        if(LocalDateTime.now().isAfter(token.get().getExpiredOn())) return null;


        return token.get().getUser();
    }

    
}