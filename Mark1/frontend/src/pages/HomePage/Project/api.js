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