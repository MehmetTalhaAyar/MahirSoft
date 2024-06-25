package com.mahirsoft.webservice.Entities.Response;

import java.util.List;
import java.util.Map;

public class GetUserRoleAndAuthorizationResponse {

    private List<GeneralAuthorizationResponse> authorizations;

    private List<GeneralUserRoleResponse> userRoles;

    private Map<Integer,List<Integer>> userRoleAuths;

    public List<GeneralAuthorizationResponse> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(List<GeneralAuthorizationResponse> authorizations) {
        this.authorizations = authorizations;
    }

    public List<GeneralUserRoleResponse> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<GeneralUserRoleResponse> userRoles) {
        this.userRoles = userRoles;
    }

    public Map<Integer, List<Integer>> getUserRoleAuths() {
        return userRoleAuths;
    }

    public void setUserRoleAuths(Map<Integer, List<Integer>> userRoleAuths) {
        this.userRoleAuths = userRoleAuths;
    }

 
    
}
