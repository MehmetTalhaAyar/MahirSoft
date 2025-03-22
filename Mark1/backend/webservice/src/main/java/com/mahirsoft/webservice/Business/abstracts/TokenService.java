package com.mahirsoft.webservice.Business.abstracts;

import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.PostUserRequest;


public interface TokenService {

    public Token createToken(PostUserRequest postUserRequest);

    public User verifyToken(String tokenWithPrefix);

    


}
