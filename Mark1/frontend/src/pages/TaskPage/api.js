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

export function updateComment(body){
    return http.put("/api/v1/comment/update",body)
}


export function updateLikeCount(commentId){
    return http.patch(`/api/v1/comment/likes/${commentId}`)
}

export function updateDescription(body){
    return http.put("/api/v1/tasks/changedescription",body);
}

export function deleteComment(commentId){
    return http.delete(`/api/v1/comment/${commentId}`);
}