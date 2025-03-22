package com.mahirsoft.webservice.Entities.Response;

public class GeneralCompanyResponse {

    private long id;

    private String name;

    private String description;

    private GeneralUserResponse manager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeneralUserResponse getManager() {
        return manager;
    }

    public void setManager(GeneralUserResponse manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    
}
