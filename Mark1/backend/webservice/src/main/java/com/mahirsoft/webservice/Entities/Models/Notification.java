package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notificationId")
    private long notificationId;

    @Column(name = "hasItBeenRead")
    private boolean hasItBeenRead = false;

    @Column(name = "message")
    private String message;

    @Column(name = "relatedEntityId")
    private long relatedEntityId;

    @Column(name = "notificationType")
    private int notificationType;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAuthentication userId;




    public static class NotificationTypes {
        public static int TASK = 1;
        public static int COMMENT = 2;
        public static int PROJECT = 3;

    }




    public long getNotificationId() {
        return notificationId;
    }




    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }




    public boolean isHasItBeenRead() {
        return hasItBeenRead;
    }




    public void setHasItBeenRead(boolean hasItBeenRead) {
        this.hasItBeenRead = hasItBeenRead;
    }




    public String getMessage() {
        return message;
    }




    public void setMessage(String message) {
        this.message = message;
    }




    public long getRelatedEntityId() {
        return relatedEntityId;
    }




    public void setRelatedEntityId(long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }




    public int getNotificationType() {
        return notificationType;
    }




    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }




    public LocalDateTime getCreatedOn() {
        return createdOn;
    }




    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }




    public UserAuthentication getUserId() {
        return userId;
    }




    public void setUserId(UserAuthentication userId) {
        this.userId = userId;
    }



    

}
