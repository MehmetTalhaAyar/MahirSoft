package com.mahirsoft.webservice.Entities.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CommentLikes")
public class CommentLike {

    @Id
    @GeneratedValue
    @Column(name = "commentLikeId")
    private long commentLikeId;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "commentId",referencedColumnName = "commentId")
    private Comment commentId;

    public long getCommentLikeId() {
        return commentLikeId;
    }

    public void setCommentLikeId(long commentLikeId) {
        this.commentLikeId = commentLikeId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }
    
}
