package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.security.DefaultUser;

@Service
public class PermissionService {


    private UserAuthenticationRepository userAuthenticationRepository;

    private StageRepository stageRepository;

    private ProjectUserRepository projectUserRepository;

    private TaskRepository taskRepository;

    private ProjectRepository projectRepository;

    


    public PermissionService(UserAuthenticationRepository userAuthenticationRepository, StageRepository stageRepository,
            ProjectUserRepository projectUserRepository, TaskRepository taskRepository,
            ProjectRepository projectRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.stageRepository = stageRepository;
        this.projectUserRepository = projectUserRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }


    public UserAuthentication isTherePermission(DefaultUser currentUser,Integer authorityNumber){

        var user = userAuthenticationRepository.findById(currentUser.getId()).orElseThrow(()-> new UserNotFoundException());

        if(authorityNumber == -1) return user; // permission listesinde bulunmayan bir işlem için user dondurme

        
        return checkUserPermission(user, authorityNumber);
        
    }

    public UserAuthentication isThereAnyOfThesePermissions(DefaultUser currentUser,List<Integer> authorityNumbers){

        var user = userAuthenticationRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException());

        return checkUserPermission(user, authorityNumbers);
    }

    public UserAuthentication isInThisProjectFindByStageId(DefaultUser currentUser,long stageId,int authorizationCode ){

        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(stageId,1);

        if(stage == null) throw new PermissionDeniedException(); // buraya uygun bir exception yazılacak

        var user = isInThisProject(currentUser,stage.getProjectId(),authorizationCode);
        
        return user;
    }

    // tamamlandı
    public UserAuthentication isInThisProjectFindByTaskId(DefaultUser currentUser,long taskId,int authorizationCode){
        
        var user = userAuthenticationRepository.findById(currentUser.getId()).orElseThrow(()-> new UserNotFoundException());

        var task = taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException());

        isInThisProjectFindByStageId(currentUser, task.getStageId().getStageId(),authorizationCode);
        
        return user;
    }

    // tamamlandı
    public UserAuthentication isInThisProject(DefaultUser currentUser,Project project,int authorizationCode ){

        var user = userAuthenticationRepository.findById(currentUser.getId()).orElseThrow(()-> new UserNotFoundException());

        if(user.getCompanyId() == null && user.getUserId() == project.getLeadingPersonId().getUserId()){

            if(authorizationCode != AuthorizationCodes.ANY_AUTHORIZATION){

                return checkUserPermission(user, authorizationCode);
            }
            return user;
        }


        if(user.getUserId() == project.getCompanyId().getManagerId().getUserId()){
            return user;
        }

        var projectUser = projectUserRepository.findByProjectIdAndUserId(project, user);

        if(projectUser == null) throw new PermissionDeniedException();

        if(authorizationCode != -1){
            return checkUserPermission(user, authorizationCode);
        }

        return user;
    }


    // tamamlandı
    public UserAuthentication isInThisProject(DefaultUser currentUser,long projectId,int authorizationCode){

        var project = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException());
        
        return isInThisProject(currentUser, project,authorizationCode);
    }

    private UserAuthentication checkUserPermission(UserAuthentication user, int authorityNumber){
        for(var authorization : user.getUserRoleId().getUserRoleAuthorizations()){
            if( Long.valueOf(authorization.getAuthorizationId().getAuthorizationId()).intValue() == authorityNumber){
                return user;
            }
        }

        throw new PermissionDeniedException();
    }

    private UserAuthentication checkUserPermission(UserAuthentication user, List<Integer> authorityNumbers){
        for(var authorization : user.getUserRoleId().getUserRoleAuthorizations()){
            if( authorityNumbers.contains(Long.valueOf(authorization.getAuthorizationId().getAuthorizationId()).intValue())){
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


        public static final List<Integer> COMPANY_AUTHORIZATIONS = List.of(
        TASK_ASSIGNMENT,
        TASK_CREATE,
        TASK_DELETE,
        STAGE_CREATE,
        STAGE_UPDATE,
        STAGE_DELETE,
        INVITATION_TO_THE_COMPANY,
        CREATING_A_REGISTERED_USER_FOR_THE_COMPANY,
        PROJECT_CREATE,
        PROJECT_UPDATE,
        PROJECT_DELETE,
        ADDING_SOMEONE_TO_THE_PROJECT,
        CHANGING_PROJECT_MANAGER,
        GRANTING_OWN_PERMISSIONS,
        GRANTING_PERMISSIONS
        );


    }

    
}
