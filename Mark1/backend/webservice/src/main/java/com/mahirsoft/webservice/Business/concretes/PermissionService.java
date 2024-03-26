package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.security.DefaultUser;

@Service
public class PermissionService {

    // "Görev atama", // 1
    // "Görev oluşturma", // 2
    // "Stage oluşturma", // 3
    // "Şirkete Katılma Daveti atabilme", // 4
    // "Şirkete kayıtlı kullanıcı oluşturma", // 5
    // "Proje oluşturma", // 6
    // "Proje sorumlusu değiştirebilme", // 7
    // "Projeye yeni birini ekleme", // 8
    // "Şirket oluşturmak için bir istek atabilmek", // 9
    // "Sadece kendi yetkilerini verebilme", // 10
    // "Yetki verme", // 11
    // "Şirket oluşturma", // 12
    // "Super Admin Yetkisi" // 13

    private UserAuthenticationService userAuthenticationService;

    public PermissionService(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    public UserAuthentication isTherePermission(DefaultUser currentUser,Integer authorityNumber){

        var user = userAuthenticationService.findById(currentUser.getId());

        if(user == null) throw new UserNotFoundException();

        if(authorityNumber == -1) return user; // permission listesinde bulunmayan bir işlem için user dondurme

        for(var authorization : user.getUserRoleId().getUserRoleAuthorizations()){
            if( Long.valueOf(authorization.getAuthorizationId().getAuthorizationId()).intValue() == authorityNumber){
                return user;
            }
        }

        throw new PermissionDeniedException();
        
    }




    
}
