package com.mahirsoft.webservice.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.DataAccess.AuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleAuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Models.Authorization;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.UserRole;
import com.mahirsoft.webservice.Entities.Models.UserRoleAuthorization;

@Configuration
public class BeginingConfiguration {
    

    private UserAuthenticationRepository userAuthenticationRepository;


    private AuthorizationRepository authorizationRepository;

    private UserRoleAuthorizationRepository userRoleAuthorizationRepository;

    private UserRoleRepository userRoleRepository;

    private PasswordEncoder passwordEncoder;




    


    public BeginingConfiguration(UserAuthenticationRepository userAuthenticationRepository,
            AuthorizationRepository authorizationRepository,
            UserRoleAuthorizationRepository userRoleAuthorizationRepository, UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder, PermissionService permissionService) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.authorizationRepository = authorizationRepository;
        this.userRoleAuthorizationRepository = userRoleAuthorizationRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public CommandLineRunner beginingCommands(){
        return args ->{
            var user = userAuthenticationRepository.findByEmail("mehmettalhaayar61@gmail.com").orElse(null);
            
            if(user == null){

                 
                List<Authorization> authorizations = new ArrayList<>();
            
            List<Integer> normalUserAuthorizations = List.of(
                AuthorizationCodes.TASK_ASSIGNMENT,
                AuthorizationCodes.TASK_CREATE,
                AuthorizationCodes.TASK_DELETE,
                AuthorizationCodes.PROJECT_CREATE,
                AuthorizationCodes.PROJECT_UPDATE,
                AuthorizationCodes.PROJECT_DELETE,
                AuthorizationCodes.COMPANY_CREATION_REQUEST
                );

            List<Integer> normalEmployeeAuthorizations = List.of(); // nothing

            List<Integer> adminAuthorizations = List.of(
                AuthorizationCodes.TASK_ASSIGNMENT,
                AuthorizationCodes.TASK_CREATE,
                AuthorizationCodes.TASK_DELETE,
                AuthorizationCodes.STAGE_CREATE,
                AuthorizationCodes.STAGE_UPDATE,
                AuthorizationCodes.STAGE_DELETE,
                AuthorizationCodes.INVITATION_TO_THE_COMPANY,
                AuthorizationCodes.CREATING_A_REGISTERED_USER_FOR_THE_COMPANY,
                AuthorizationCodes.PROJECT_CREATE,
                AuthorizationCodes.PROJECT_UPDATE,
                AuthorizationCodes.PROJECT_DELETE,
                AuthorizationCodes.ADDING_SOMEONE_TO_THE_PROJECT,
                AuthorizationCodes.CHANGING_PROJECT_MANAGER,
                AuthorizationCodes.GRANTING_PERMISSIONS
                );

            List<Integer> superAdminAuthorizations = List.of(
                AuthorizationCodes.CREATE_COMPANY,
                AuthorizationCodes.SUPER_ADMIN
            );

            List<String> authorizationNameList = List.of(
                "Görev atama", // 1
                "Görev oluşturma", // 2
                "Görev silme", // 3
                "Stage oluşturma", // 4
                "Stage güncelleme", // 5
                "Stage silme" , // 6
                "Şirkete Katılma Daveti atabilme", // 7
                "Şirkete kayıtlı kullanıcı oluşturma", // 8
                "Proje oluşturma", // 9
                "Proje güncelleme", // 10
                "Proje silme", // 11
                "Projeye yeni birini ekleme", // 12
                "Proje sorumlusu değiştirebilme", // 13
                "Şirket oluşturmak için bir istek atabilmek", // 14
                "Sadece kendi yetkilerini verebilme", // 15
                "Yetki verme", // 16
                "Şirket oluşturma", // 17
                "Super Admin Yetkisi" // 18
                );

            for(var authorizationDefination : authorizationNameList){
                Authorization authorization = new Authorization(); // 1 
                authorization.setDefinition(authorizationDefination);
                authorizationRepository.save(authorization);
                authorizations.add(authorization);
            }


            UserRole normalUser = new UserRole();
            normalUser.setName("User without company");
            userRoleRepository.save(normalUser);
            authorityPairing(authorizations, normalUser, normalUserAuthorizations);

            
            UserRole normalEmployee = new UserRole();
            normalEmployee.setName("New user for company");
            userRoleRepository.save(normalEmployee);
            authorityPairing(authorizations, normalEmployee, normalEmployeeAuthorizations);


            UserRole admin = new UserRole();
            admin.setName("Company Administrator");
            userRoleRepository.save(admin);
            authorityPairing(authorizations, admin, adminAuthorizations);

            UserRole superAdminRole = new UserRole();
            superAdminRole.setName("Super Administrator");
            userRoleRepository.save(superAdminRole);
            authorityPairing(authorizations, superAdminRole, superAdminAuthorizations);
            
            UserAuthentication superAdmin = new UserAuthentication();
            superAdmin.setName("Mehmet Talha");
            superAdmin.setSurname("AYAR");
            superAdmin.setEmail("mehmettalhaayar61@gmail.com");
            superAdmin.setPassword(passwordEncoder.encode("1234Tr1234+"));
            superAdmin.setGsm("1110001100");
            superAdmin.setTitle(superAdminRole.getName());
            superAdmin.setUserRoleId(superAdminRole);
            
            userAuthenticationRepository.save(superAdmin);




            }
             
            

        };


    }


    private void authorityPairing(List<Authorization> authorizations,UserRole userRole,List<Integer> authorities){

        
        for(var authorization : authorizations){
            if(authorities.contains(Long.valueOf(authorization.getAuthorizationId()).intValue())){
                UserRoleAuthorization newUserRoleAuthorization = new UserRoleAuthorization();
                newUserRoleAuthorization.setAuthorizationId(authorization);
                newUserRoleAuthorization.setUserRoleId(userRole);
                userRoleAuthorizationRepository.save(newUserRoleAuthorization);

            }

        }
    }
}
