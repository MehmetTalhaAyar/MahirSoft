import { createContext, useContext, useEffect, useReducer } from "react";
import { loadAuthState, loadToken, storeAuthState, storeToken } from "./storage";
import { loadSessionAuthState, storeSessionAuthState ,deleteSessionAuthState, loadSessionToken } from "./sessionstorage";
import { setSessionToken, setToken } from "../lib/http";
import { act } from "react";

export const AuthContext = createContext();

export const AuthDispatchContext = createContext();

export function useAuthState(){
    return useContext(AuthContext);
}

export function useAuthDispatch(){
    return useContext(AuthDispatchContext);
}

const authReducer = (authState,action) => {
    switch(action.type){
        case 'login-success':
            storeSessionAuthState(action.data.user)
            setSessionToken(action.data.token)

            return action.data.user;
        case 'remember-login-success':
            storeAuthState(action.data.user)
            setToken(action.data.token)
            return action.data;
        case 'logout-success':
            deleteSessionAuthState()
            storeAuthState({ userId: 0 })
            setSessionToken()
            setToken()
            return { userId: 0 }
        case 'session-storage':
            setSessionToken(loadSessionToken())
            return loadSessionAuthState();
        case 'update-image':
            storeSessionAuthState({...authState ,image :action.image})
            return loadSessionAuthState();

        
        default:
            throw new Error(`unknown action : ${action.type}`)
    }
}

export function AuthenticationContext({children}){
    const [authState,dispatch] = useReducer(authReducer,loadAuthState());

    useEffect(()=>{
        
        if(!(authState.userId > 0)) {
            dispatch({type:'session-storage'});
            
            
        }
        
    },[])

    // useEffect(()=>{
    //     console.log("buraya girdix")
    //     storeSessionAuthState(authState)
        

    // },[authState])

    return <AuthContext.Provider value={authState}>
        <AuthDispatchContext.Provider value={dispatch}>
            {children}
        </AuthDispatchContext.Provider>
    </AuthContext.Provider>


}