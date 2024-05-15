import http from "../../../lib/http";


export function EditProfileImage(body){
    return http.put("/api/v1/userauthentication/updateimage",body);
}