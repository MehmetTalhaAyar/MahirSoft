import http from "../../../lib/http";


export function createUser(body){
    http.post("api/v1/userauthentication/createuser",body)

}