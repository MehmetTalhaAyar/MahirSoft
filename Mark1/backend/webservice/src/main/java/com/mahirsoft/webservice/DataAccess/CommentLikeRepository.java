package com.mahirsoft.webservice.DataAccess;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Comment;
import com.mahirsoft.webservice.Entities.Models.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {


    long countByCommentId(Comment comment);

}
