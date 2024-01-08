
export function storeAuthState(auth){
    localStorage.setItem('auth',JSON.stringify(auth));
}

export function loadAuthState(){
    const defaultState = { userId:0 };
    const authStateInStorage = localStorage.getItem('auth');

    if(!authStateInStorage) return defaultState;
    
    try {
        return JSON.parse(authStateInStorage);
    } catch (error) {
        return defaultState;
    }

}