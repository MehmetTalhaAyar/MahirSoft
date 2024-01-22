package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.CommentRepository;
import com.mahirsoft.webservice.Entities.Models.Comment;

@Service
public class CommentService {

    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public Comment createComment(Comment comment){
       return commentRepository.save(comment);

    }


    
    
}
