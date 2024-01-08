


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