package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    
}
