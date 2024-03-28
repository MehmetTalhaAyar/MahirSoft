package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostImageUpdateRequest;
import com.mahirsoft.webservice.Entities.Requests.PostSearchCompanyMembersRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;



@Service
public class UserAuthenticationService {

    private UserAuthenticationRepository userAuthenticationRepository;

    private PasswordEncoder passwordEncoder;

    private FileService fileService;

    private UserRoleRepository userRoleRepository;
    
    

    public UserAuthenticationService(UserAuthenticationRepository userAuthenticationRepository,
            PasswordEncoder passwordEncoder, FileService fileService, UserRoleRepository userRoleRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
        this.userRoleRepository = userRoleRepository;
    }

    public UserAuthentication save(UserAuthentication userAuthentication){

        userAuthentication.setPassword(passwordEncoder.encode(userAuthentication.getPassword()));
        userAuthentication.setCreatedById(userAuthentication); // kayıt olan kullanıcı kendisini oluşturmuş oluyor

        var userRole = userRoleRepository.findById(1); // 1 new user without company

        if(userRole == null) return null;

        userAuthentication.setUserRoleId(userRole);


        return userAuthenticationRepository.save(userAuthentication);
    
    }

    public UserAuthentication findByEmail(String email){
        return userAuthenticationRepository.findByEmail(email);
    }

    public UserAuthentication getUserInfo(PostUserAuthenticationRequest PostUserAuthenticationRequest){
        
        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmail(PostUserAuthenticationRequest.getEmail());
        if (userAuthentication == null) return null;

        if(!passwordEncoder.matches(PostUserAuthenticationRequest.getPassword(), userAuthentication.getPassword())) return null;

        return userAuthentication;

                
    }


    public List<UserAuthentication> getAllUsers() {

        
        return userAuthenticationRepository.findAll();
    }

    public UserAuthentication findById(long id){
        return userAuthenticationRepository.findById(id);
    }

    public UserAuthentication addUserToCompany(UserAuthentication userAuthentication){

        var userRole = userRoleRepository.findById(2); // new user for company
        
        if(userRole  == null) return null;
        userAuthentication.setPassword(passwordEncoder.encode(userAuthentication.getPassword()));
        userAuthentication.setUserRoleId(userRole);

        return userAuthenticationRepository.save(userAuthentication);
    }

    public List<UserAuthentication> getUsersBycompany(Company company,PostSearchCompanyMembersRequest searchKey ){

        if(!searchKey.getSearchKey().strip().isBlank()){
            return userAuthenticationRepository.findFirst5ByCompanyIdAndNameContaining(company, searchKey.getSearchKey()); 
        }

        return userAuthenticationRepository.findFirst5BycompanyId(company);
    }


    public UserAuthentication updateUserImage(UserAuthentication user,PostImageUpdateRequest postImageUpdateRequest) {


        String image = fileService.saveBase64StringAsFile(postImageUpdateRequest.getImage());

        if(image == null) return null;


        user.setImage(image);

        return userAuthenticationRepository.save(user);

    }

    public UserAuthentication updateUserWithCompany(UserAuthentication manager) {
        
        var userRole = userRoleRepository.findById(3); // company administrator

        if(userRole  == null) return null;

        manager.setUserRoleId(userRole);
        manager.setTitle("Administrator");

        return userAuthenticationRepository.save(manager);
    }

    
}
