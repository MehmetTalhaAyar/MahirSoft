import http from "../../../lib/http";

export function addTask(body){
    return http.post("/api/v1/tasks/addtask",body)
}

export function getAllTask(){
    return http.get("/api/v1/tasks/getalltasks")
}