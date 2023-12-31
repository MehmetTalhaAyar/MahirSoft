import axios from "axios";

export function addTask(body){
    return axios.post("/api/v1/tasks/addtask",body)
}

export function getAllTask(){
    return axios.get("/api/v1/tasks/getalltasks")
}