package com.mahirsoft.webservice.Business.abstracts;

import com.mahirsoft.webservice.Entities.Models.Token;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface TokenService {

    public Token createToken(UserAuthentication userAuthentication);

    


}
