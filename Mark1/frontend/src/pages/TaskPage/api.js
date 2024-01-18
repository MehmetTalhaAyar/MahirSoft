import http from "../../lib/http";


export function getTaskInfo(taskId){
    return http.get(`/api/v1/tasks/${taskId}`)
}

export function deleteTaskById(id){
    return http.delete(`/api/v1/taskstage/${id}`)
}