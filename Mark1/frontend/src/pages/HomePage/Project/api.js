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
