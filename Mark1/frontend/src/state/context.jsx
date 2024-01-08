import { createContext, useContext, useEffect, useReducer } from "react";
import { loadAuthState, storeAuthState } from "./storage";
import { loadSessionAuthState, storeSessionAuthState ,deleteSessionAuthState } from "./sessionstorage";

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
            storeSessionAuthState(action.data)
            return action.data;
        case 'remember-login-success':
            storeAuthState(action.data)
            return action.data;
        case 'logout-success':
            deleteSessionAuthState()
            storeAuthState({ userId: 0 })
            return { userId: 0 }
        case 'session-storage':
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

    return <AuthContext.Provider value={authState}>
        <AuthDispatchContext.Provider value={dispatch}>
            {children}
        </AuthDispatchContext.Provider>
    </AuthContext.Provider>


}