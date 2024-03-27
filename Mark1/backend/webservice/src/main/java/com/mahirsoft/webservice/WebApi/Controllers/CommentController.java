package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.CommentService;
import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.TaskService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Models.Comment;
import com.mahirsoft.webservice.Entities.Requests.CreateCommentRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralCommentResponse;
import com.mahirsoft.webservice.security.DefaultUser;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    CommentService commentService;

    PermissionService permissionService;

    TaskService taskService;


    public CommentController(CommentService commentService, PermissionService permissionService,
            TaskService taskService) {
        this.commentService = commentService;
        this.permissionService = permissionService;
        this.taskService = taskService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CreateCommentRequest createCommentRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var linkedTask = taskService.findById(createCommentRequest.getLinkedTaskId());

        if(linkedTask == null) return new ResponseEntity<String>("SomeThing went wrong!", HttpStatusCode.valueOf(400));

        var writtenBy = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION); // yorum yapma yetkisi olmadığı için -1 konuldu.

        Comment newComment = new Comment();
        newComment.setContent(createCommentRequest.getContent());
        newComment.setLinkedTaskId(linkedTask);
        newComment.setWrittenById(writtenBy);

        var createdComment = commentService.createComment(newComment);

        GeneralCommentResponse generalCommentResponse = createdComment.toGeneralCommentResponse();

        return new ResponseEntity<GeneralCommentResponse>(generalCommentResponse, HttpStatusCode.valueOf(201));

        

    }
    
}
