import React, { useEffect, useState } from "react";
import { AiOutlineClose } from "react-icons/ai";
import { FaCheck } from "react-icons/fa6";
import { IoMdAdd } from "react-icons/io";
import { MdDelete, MdEdit } from "react-icons/md";
import { FaList } from "react-icons/fa6";

import "./projectStagesDetails.css";
import { createStage, updateStage } from "./api";

export default function ProjectStagesDetails({stages,totalTaskCount,projectId}) {
  const [newStage, setNewStage] = useState(false);
  const [stageName, setStageName] = useState("");
  const [editStage, setEditStage] = useState(null);
  const [stagesName, setStagesName] = useState([]);
  const [totalTasks,setTotalTasks] = useState(undefined);
  const [updatedTask,setUpdatedTask] = useState(undefined);


  const handleSave = async () => {

    // burada bir evet hayır yeri açılacak
    if (stageName && projectId !== undefined) {
      const response = await createStage(projectId,{name : stageName})

      if(response.status === 201) {
        console.log(response.data);
        setStagesName([{ name: stageName }, ...stagesName]);
        setStageName("");

      }

      
      setNewStage(false);
    }
  };

  useEffect(()=>{
    setTotalTasks(totalTaskCount);
    console.log(totalTasks)
  },[totalTaskCount])

  const handleNewStage = () => {
    setNewStage(true);
  };

  const handleCancel = () => {
    setNewStage(false);
    setStageName("");
  };

  const handleEdit = (index) => {
    setEditStage(index);
    
  };

  const handleDelete = (index) => {
    setStagesName(stagesName.filter((_, i) => i !== index));
    setEditStage(null);
  };

  const handleUpdateStageName = (index, newName) => {
    setUpdatedTask({name : newName, index : index})
    
  };
  const handleSaveEdit = async () => {

    const updatedStages = [...stagesName];

    const response = await updateStage({
      stageId : updatedStages[updatedTask.index].id,
      name : updatedTask.name
    });

    if(response.status === 200){
      console.log(response.data)
      updatedStages[index].name = updatedTask.name;
      setStagesName(updatedStages);
    }
    setEditStage(null); // Exit editing mode
    setUpdatedTask(undefined);
  };

  useEffect(()=>{
    setStagesName(stages);
  },[stages])

  return (
    <section className="project_stages">
      <div className="project_stage_container">
        <div className="stages_container">
          <FaList />
          <h1 className="project_stage_title">Project Stages</h1>
        </div>
        <span onClick={handleNewStage} className="add_stages">
          <IoMdAdd />
          Add
        </span>
      </div>
      <div className="stages_list_container">
        {newStage ? (
          <div className="input_container">
            <input
              value={stageName}
              onChange={(e) => setStageName(e.target.value)}
            />
            <div className="save_cancel_stages">
              <span onClick={handleSave} className="check">
                <FaCheck />
              </span>
              <span onClick={handleCancel} className="remove">
                <AiOutlineClose />
              </span>
            </div>
          </div>
        ) : null}
        <ul>
          {stagesName.map((item, index) => (
            <li className="stages_list" key={index}>
              {editStage === index ? (
                <input
                  className="edit_input_stage"
                  value={updatedTask !== undefined ? updatedTask.name : stagesName[index].name}
                  onChange={(e) => handleUpdateStageName(index, e.target.value)}
                />
              ) : (
                <span className="stage_name">{item.name}</span>
              )}
              <div className="update_remove">
                {editStage === index ? (
                  <span onClick={handleSave} className="check">
                    <FaCheck onClick={handleSaveEdit} />
                  </span>
                ) : (
                  <MdEdit
                    onClick={() => handleEdit(index)}
                    className="edit_stage"
                  />
                )}
                <MdDelete
                  onClick={() => handleDelete(index)}
                  className="delete_stage"
                />
              </div>
            </li>
          ))}
        </ul>
      </div>
      <div className="project_tasks_number">
        <div className="project_total_task">
          <label>Total Task</label>
          <span>{totalTaskCount !== undefined ? totalTaskCount.totalTaskCount : 0}</span>
        </div>
        <div className="project_finished_task">
          <label>Finished Task</label>
          <span>{totalTaskCount !== undefined ? totalTaskCount.finishedTask : 0}</span>
        </div>
        <div className="project_falied_task">
          <label>Failed Task</label>
          <span>{totalTaskCount !== undefined ? totalTaskCount.failedTask : 0}</span>
        </div>
      </div>
    </section>
  );
}
