import http from "../../../lib/http";

export function addTask(stageId,body){
    return http.post(`/api/v1/taskstage/${stageId}`,body)
}

export function getProjectDetails(projectId){
    return http.get(`/api/v1/projects/details/${projectId}`)
}

export function updateTaskStage(body){
    return http.put("/api/v1/taskstage/updatetaskstage",body);
}