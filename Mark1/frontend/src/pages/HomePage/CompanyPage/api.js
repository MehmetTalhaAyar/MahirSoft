import http from "../../../lib/http";


export function createUser(body){
    return http.post("/api/v1/company/createcompanymember",body)

}

export function companyRequest(body){
    return http.post("/api/v1/company/create/request",body);
}