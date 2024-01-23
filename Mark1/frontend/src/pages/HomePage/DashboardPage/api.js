import http from "../../../lib/http";


export function getResponsibleTaskCount(){
    return http.get("/api/v1/userauthentication/taskcount")
}