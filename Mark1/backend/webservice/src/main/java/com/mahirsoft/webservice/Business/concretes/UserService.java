package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.PostImageUpdateRequest;
import com.mahirsoft.webservice.Entities.Requests.PostSearchCompanyMembersRequest;




@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private FileService fileService;

    private UserRoleRepository userRoleRepository;
    
    

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder, FileService fileService, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
        this.userRoleRepository = userRoleRepository;
    }

    

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException());
    }


    public List<User> getAllUsers() {

        
        return userRepository.findAll();
    }

    public User addUserToCompany(User user){

        var userRole = userRoleRepository.findById(2); // new user for company
        
        if(userRole  == null) return null;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRoleId(userRole);

        return userRepository.save(user);
    }

    public List<User> getUsersBycompany(Company company,PostSearchCompanyMembersRequest searchKey ){

        if(!searchKey.getSearchKey().strip().isBlank()){
            return userRepository.findFirst5ByCompanyIdAndNameContaining(company, searchKey.getSearchKey()); 
        }

        return userRepository.findFirst5BycompanyId(company);
    }


    public String updateUserImage(User user,PostImageUpdateRequest postImageUpdateRequest) {


        String image = fileService.saveBase64StringAsFile(postImageUpdateRequest.getImage());

        if(image == null) return null;


        user.setImage(image);

        userRepository.save(user);
        return image;

    }

    public User updateUserWithCompany(User manager) {
        
        var userRole = userRoleRepository.findById(3); // company administrator

        if(userRole  == null) return null;

        manager.setUserRoleId(userRole);
        manager.setTitle("Administrator");

        return userRepository.save(manager);
    }

    
}
