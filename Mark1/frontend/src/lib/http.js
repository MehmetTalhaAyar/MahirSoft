import axios from "axios";
import { loadToken, storeToken } from "../state/storage";
import { loadSessionToken, storeSessionToken } from "../state/sessionstorage";

const http = axios.create();

let authToken = loadToken();



export function setSessionToken(token){
    authToken = token;
    storeSessionToken(token)
}

export function setToken(token){
    authToken = token;
    storeToken(authToken)
}

http.interceptors.request.use((config)=>{

    if(authToken){
        config.headers["Authorization"] = `${authToken.prefix} ${authToken.token}`
    }

    return config;
})


export default http;