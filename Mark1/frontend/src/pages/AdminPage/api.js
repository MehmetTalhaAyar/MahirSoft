import http from "../../lib/http";


export function getAllRequests(){
    return http.get("/api/v1/company/all/requests");
}

export function replyRequests(body){
    return http.post("/api/v1/company/create/request/response",body);
}

