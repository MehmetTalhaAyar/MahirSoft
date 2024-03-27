package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.security.DefaultUser;

@Service
public class PermissionService {


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




    public static class AuthorizationCodes {

        public static final int ANY_AUTHORIZATION = -1;
        public static final int TASK_ASSIGNMENT = 1; // görev atama
        public static final int TASK_CREATE = 2; // görev oluşturma
        public static final int TASK_DELETE = 3; // görev silme
        public static final int STAGE_CREATE = 4; // stage oluşturma
        public static final int STAGE_UPDATE = 5; // stage güncelleme
        public static final int STAGE_DELETE = 6; // stage silme
        public static final int INVITATION_TO_THE_COMPANY = 7; // şirkete katılma daveti atmak
        public static final int CREATING_A_REGISTERED_USER_FOR_THE_COMPANY = 8; // şirkete kayıtlı kullanıcı oluşturma
        public static final int PROJECT_CREATE = 9; // proje oluşturma
        public static final int PROJECT_UPDATE = 10; // proje güncelleme
        public static final int PROJECT_DELETE = 11; // proje silme 
        public static final int ADDING_SOMEONE_TO_THE_PROJECT = 12; // Birini projeye ekleme
        public static final int CHANGING_PROJECT_MANAGER = 13; // proje sorumlusu değiştirme
        public static final int COMPANY_CREATION_REQUEST = 14; // şirket oluşturma isteği atabilme
        public static final int GRANTING_OWN_PERMISSIONS = 15; // kendi yetkilerini verebilme
        public static final int GRANTING_PERMISSIONS = 16; // yetki verebilme
        public static final int CREATE_COMPANY = 17; // şirket oluşturma
        public static final int SUPER_ADMIN = 18; // super admin

    }

    
}
