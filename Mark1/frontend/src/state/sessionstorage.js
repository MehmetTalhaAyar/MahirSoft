import { json } from "react-router-dom";



export function storeSessionToken(token){
    if(token){
        sessionStorage.setItem('token',JSON.stringify(token));
    }
    else{
        sessionStorage.removeItem('token');
    }
}

export function loadSessionToken(){
    const authTokenInSession = sessionStorage.getItem('token')
    if(!authTokenInSession) return null
    try {
        return JSON.parse(authTokenInSession);
    } catch (error) {
        return null;
    }
}


export function storeSessionAuthState(auth){
    sessionStorage.setItem("authentication-info",JSON.stringify(auth))
}

export function loadSessionAuthState(){
    const defaultState = { userId:0 };
    const authStateInStorage = sessionStorage.getItem('authentication-info');

    if(!authStateInStorage) return defaultState;
    
    try {
        return JSON.parse(authStateInStorage);
    } catch (error) {
        return defaultState;
    }

}

export function deleteSessionAuthState(){
    sessionStorage.removeItem('authentication-info')
}