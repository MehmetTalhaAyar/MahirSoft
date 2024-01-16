import http from "../../../lib/http";

export function addTask(stageId,body){
    return http.post(`/api/v1/taskstage/${stageId}`,body)
}

export function getTasks(stageId){
    return http.get(`/api/v1/taskstage/alltasks/${stageId}`)
}