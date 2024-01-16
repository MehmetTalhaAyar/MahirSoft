import http from "../../../lib/http";


export function getCompanyMembers(){
    return http.get("/api/v1/company/members")
}

export function getCompanyProjects(){
    return http.get("/api/v1/company/projects")
}