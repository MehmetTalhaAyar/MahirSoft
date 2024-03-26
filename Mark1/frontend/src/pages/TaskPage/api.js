import http from "../../lib/http";


export function getTaskInfo(taskId){
    return http.get(`/api/v1/tasks/${taskId}`)
}

export function deleteTaskById(id){
    return http.delete(`/api/v1/taskstage/${id}`)
}

export function addComment(body){
    return http.post("/api/v1/comment/add",body)
}

export function getprojectMembers(body){
    return http.post(`/api/v1/projectandstage/getallmembersandstage`,body)
}

export function updateTaskInfo(body,taskId){
    return http.put(`/api/v1/projectandstage/updatetask/${taskId}`,body)
}