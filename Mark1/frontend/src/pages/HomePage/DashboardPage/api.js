import axios from "axios";

export function addTask(body){
    return axios.post("http://localhost:5173/api/v1/tasks/addtask",body)
}

export function getAllTask(){
    return axios.get("http://localhost:5173/api/v1/tasks/getalltasks")
}