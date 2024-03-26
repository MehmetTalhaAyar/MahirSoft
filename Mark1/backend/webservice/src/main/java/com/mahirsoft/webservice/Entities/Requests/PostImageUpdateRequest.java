package com.mahirsoft.webservice.Entities.Requests;

import com.mahirsoft.webservice.Entities.Validations.Annotations.FileType;

public class PostImageUpdateRequest {

    @FileType(types = {"png","jpg","jpeg"})
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
