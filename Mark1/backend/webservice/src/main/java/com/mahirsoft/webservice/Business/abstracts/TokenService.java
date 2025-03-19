package com.mahirsoft.webservice.Business.abstracts;

import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;


public interface TokenService {

    public Token createToken(PostUserAuthenticationRequest userAuthentication);

    public UserAuthentication verifyToken(String tokenWithPrefix);

    


}
