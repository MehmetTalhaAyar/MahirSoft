package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.CommentLikeRepository;
import com.mahirsoft.webservice.DataAccess.CommentRepository;
import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Comment;
import com.mahirsoft.webservice.Entities.Models.CommentLike;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateCommentRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUpdateCommentRequest;
import com.mahirsoft.webservice.Entities.Response.CommentLikeCountResponse;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    private CommentLikeRepository commentLikeRepository;

    private TaskRepository taskRepository;

    

    


    public CommentService(CommentRepository commentRepository, CommentLikeRepository commentLikeRepository,
            TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.taskRepository = taskRepository;
    }


    public Comment createComment(CreateCommentRequest createCommentRequest,UserAuthentication writtenBy){

        Task relatedTask = taskRepository.findById(createCommentRequest.getLinkedTaskId()).orElseThrow(()-> new ResourceNotFoundException()); 

        Comment comment = new Comment();
        comment.setContent(createCommentRequest.getContent());
        comment.setLinkedTaskId(relatedTask);
        comment.setWrittenById(writtenBy);

        return commentRepository.save(comment);

    }


    public Comment UpdateComment(PostUpdateCommentRequest postUpdateCommentRequest,UserAuthentication user) {
        
        var comment = commentRepository.findById(postUpdateCommentRequest.getCommentId()).orElse(null);

        if(comment == null) return null;

        if(comment.getWrittenById().getUserId() != user.getUserId()) throw new PermissionDeniedException(); // buradaki hatayı farklı bir hata ile değiştirebiliriz

        comment.setContent(postUpdateCommentRequest.getContent());

        return commentRepository.save(comment);
    }


    public CommentLikeCountResponse updateLikeCount(UserAuthentication user,Comment comment) {
        CommentLikeCountResponse commentLikeCountResponse = new CommentLikeCountResponse();

        for(var commentLike : comment.getLikes()){
            if(commentLike.getUserId().getUserId() == user.getUserId()){
                //burada like silme islemi yapacağız
                commentLikeRepository.delete(commentLike); // silindi
                
                commentLikeCountResponse.setLikeCount(commentLikeRepository.countByCommentId(comment));
                return commentLikeCountResponse;
            } 
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setCommentId(comment);
        commentLike.setUserId(user);
        commentLikeRepository.save(commentLike);

        commentLikeCountResponse.setLikeCount(commentLikeRepository.countByCommentId(comment));
        return commentLikeCountResponse;

    }


    public Comment findById(long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }


    public Comment softDeleteComment(long commentId,UserAuthentication user) {
        
        var comment = commentRepository.findById(commentId).orElse(null);

        if(comment == null) return null;

        if(comment.getWrittenById().getUserId() != user.getUserId()) throw new PermissionDeniedException(); // buradaki hatayı farklı bir hata ile değiştirebiliriz

        comment.setDeletionStateCode(1);
        
        return commentRepository.save(comment);



    }


    
    
}
