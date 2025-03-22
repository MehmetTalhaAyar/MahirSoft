package com.mahirsoft.webservice.Business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PostGetStageAndProjectMembersRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageNameRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageSequenceRequest;
import com.mahirsoft.webservice.Entities.Response.ProjectMembersAndStageResponse;
import com.mahirsoft.webservice.Entities.Response.PutUpdateStageSequenceResponse;
import com.mahirsoft.webservice.Entities.Response.UserAuthenticationResponse;

import jakarta.validation.Valid;


@Service
public class StageService {

    private StageRepository stageRepository;

    private ProjectRepository projectRepository;

    private ProjectUserRepository projectUserRepository;

   
    

    public StageService(StageRepository stageRepository, ProjectRepository projectRepository,
            ProjectUserRepository projectUserRepository) {
        this.stageRepository = stageRepository;
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
    }

    public Stage getStage(long id){
        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(id,1);
        if(stage == null){
            return null;
        }
        return stage;     
    }

    public Stage createStage(CreateStageRequest createStageRequest,User user,long projectId){
        
        var project = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException());

        Stage stage = new Stage();
        stage.setCreatedById(user);
        stage.setName(createStageRequest.getName());
        stage.setProjectId(project);
        stage.setSequence(stageRepository.findFirstByProjectIdAndDeletionStateCodeNotOrderBySequenceDesc(project,1).getSequence() + 1);    
        
        return stageRepository.save(stage);
    }

    public Stage updateStage(PutUpdateStageNameRequest putUpdateStageNameRequest) {
        
        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(putUpdateStageNameRequest.getStageId(),1);

        if(stage == null) return null;

        stage.setName(putUpdateStageNameRequest.getName());

        return stageRepository.save(stage);

    }

    public PutUpdateStageSequenceResponse updateStageSequence(@Valid PutUpdateStageSequenceRequest putUpdateStageSequenceRequest) {
        
        var stageGoingUp = stageRepository.findByStageIdAndDeletionStateCodeNot(putUpdateStageSequenceRequest.getStageGoingUp(),1);

        if(stageGoingUp == null) return null;

        var stageGoingDown = stageRepository.findByStageIdAndDeletionStateCodeNot(putUpdateStageSequenceRequest.getStageGoingDown(),1);

        if(stageGoingDown == null) return null;

        int downSequence = stageGoingUp.getSequence(); // yukarı çıkacak olan stage in sequence i  diğerinden daha büyük

        stageGoingUp.setSequence(stageGoingDown.getSequence());
        stageGoingDown.setSequence(downSequence); // down 2


        var newDown = stageRepository.save(stageGoingDown);
        var newUp = stageRepository.save(stageGoingUp);

        PutUpdateStageSequenceResponse putUpdateStageSequenceResponse = new PutUpdateStageSequenceResponse();
        putUpdateStageSequenceResponse.setNewDown(newDown.toGeneralStageResponse());
        putUpdateStageSequenceResponse.setNewUp(newUp.toGeneralStageResponse());


        return putUpdateStageSequenceResponse; 
    }

    public ProjectMembersAndStageResponse getProjectMembersAndStageByStageId(PostGetStageAndProjectMembersRequest postGetStageAndProjectMembersRequest) {
       
        List<UserAuthenticationResponse> users = new ArrayList<>();
        ProjectMembersAndStageResponse projectMembersAndStageResponse = new ProjectMembersAndStageResponse();

        var stage = stageRepository.findById(postGetStageAndProjectMembersRequest.getStageId()).orElseThrow(()-> new ResourceNotFoundException());

        if(postGetStageAndProjectMembersRequest.getSearchKey().strip().isBlank()){
            var projectUsers =  projectUserRepository.findFirst5ByProjectId(stage.getProjectId());

            for(var eleman : projectUsers){
                users.add(eleman.getUserId().toUserAuthenticationResponse());
            }

            projectMembersAndStageResponse.setStages(stage.getProjectId().toStageResponses());
            projectMembersAndStageResponse.setUsers(users);

            return projectMembersAndStageResponse;
        }

        
        var projectMembers = projectUserRepository.findFirst5ByProjectIdAndNameContaining(stage.getProjectId(), postGetStageAndProjectMembersRequest.getSearchKey());
        

        for(var projectUser : projectMembers){
            users.add(projectUser.getUserId().toUserAuthenticationResponse());
        }


        
        projectMembersAndStageResponse.setStages(stage.getProjectId().toStageResponses());
        projectMembersAndStageResponse.setUsers(users);

        return projectMembersAndStageResponse;


    }

    public Stage softDeleteStage(long stageId) {

        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(stageId,1);

        if(stage == null) return null;

        stage.setDeletionStateCode(1);

        return stageRepository.save(stage);
    }

    

    
}
