import axios from "axios";

export function signIn(body) {
  return axios.post("/api/v1/userauthentication/user", body);
}

export function signUp(body){
  return axios.post("/api/v1/userauthentication/add",body);
}
