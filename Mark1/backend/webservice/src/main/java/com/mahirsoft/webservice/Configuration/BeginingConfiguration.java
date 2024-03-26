package com.mahirsoft.webservice.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            PasswordEncoder passwordEncoder) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.authorizationRepository = authorizationRepository;
        this.userRoleAuthorizationRepository = userRoleAuthorizationRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public CommandLineRunner beginingCommands(){
        return args ->{

            var user = userAuthenticationRepository.findByEmail("mehmettalhaayar61@gmail.com");

            if(user == null){

                List<Authorization> authorizations = new ArrayList<>();
            
            List<Integer> normalUserAuthorizations = List.of(2,3,6,9);

            List<Integer> normalEmployeeAuthorizations = List.of(); // nothing

            List<Integer> adminAuthorizations = List.of(1,2,3,4,5,6,7,8,11);

            List<Integer> superAdminAuthorizations = List.of(12,13);

            List<String> authorizationNameList = List.of(
                "Görev atama", // 1
                "Görev oluşturma", // 2
                "Stage oluşturma", // 3
                "Şirkete Katılma Daveti atabilme", // 4
                "Şirkete kayıtlı kullanıcı oluşturma", // 5
                "Proje oluşturma", // 6
                "Proje sorumlusu değiştirebilme", // 7
                "Projeye yeni birini ekleme", // 8
                "Şirket oluşturmak için bir istek atabilmek", // 9
                "Sadece kendi yetkilerini verebilme", // 10
                "Yetki verme", // 11
                "Şirket oluşturma", // 12
                "Super Admin Yetkisi" // 13
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
