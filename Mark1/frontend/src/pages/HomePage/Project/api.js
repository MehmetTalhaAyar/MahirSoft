import http from "../../../lib/http";


export function getCompanyMembers(searchBody){
    return http.post("/api/v1/company/members",searchBody);
}

export function getCompanyProjects(){
    return http.get("/api/v1/company/projects");
}

export function createProject(body){
    return http.post("/api/v1/projectandstage/createproject",body);
}

export function deleteProjectById(id){
    return http.delete(`/api/v1/projectuser/${id}`)
}

export function projectDetails(projectId){
    return http.get(`/api/v1/projects/details/project/${projectId}`)
}

export function updateProjectDescription(projectId,body){
    return http.put(`/api/v1/projects/description/${projectId}`,body)
}

export function updateProjectName(projectId,body){
    return http.put(`/api/v1/projects/name/${projectId}`,body)
}

export function createStage(projectId,body){
    return http.post(`/api/v1/stage/create/${projectId}`,body)
}

export function updateStage(body){
    return http.put("/api/v1/stage/update",body);
}

export function getAuthorization(){
    return http.get("/api/v1/authority/role");
}

export function createRole(body){
    return http.post("/api/v1/authority/create",body);
}

export function updateRole(body){
    return http.post("/api/v1/authority/update",body);
}

export function getAvaibleMembers(body){
    return http.post("/api/v1/projects/members",body);
}

export function AddingANewMember(body){
    return http.post("/api/v1/projects/add/employee",body);
}

export function removeMember(body){
    return http.post("/api/v1/projects/delete",body);
}

export function getAvaibleRoles(body){
    return http.post("/api/v1/company/roles",body);
}

export function handleGrantRole(body){
    return http.post("/api/v1/company/grant/role",body);
}

export function handleUpdateSequence(body){
    return http.put("/api/v1/stage/update/sequence",body);
}

export function deleteStage(stageId){
    return http.delete(`/api/v1/stage/delete/${stageId}`)
}