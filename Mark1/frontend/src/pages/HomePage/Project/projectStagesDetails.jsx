import React, { useEffect, useState } from "react";
import { AiOutlineClose } from "react-icons/ai";
import { FaCheck } from "react-icons/fa6";
import { IoMdAdd } from "react-icons/io";
import { MdDelete, MdEdit } from "react-icons/md";
import { FaList } from "react-icons/fa6";
import { IoIosWarning } from "react-icons/io";
import { RiEdit2Fill } from "react-icons/ri";

import "./projectStagesDetails.css";
import { createStage, deleteStage, handleUpdateSequence, updateStage } from "./api";
import WarningModal from "./WarningModal";
import { FaCaretDown, FaCaretUp } from "react-icons/fa";

export default function ProjectStagesDetails({
  stages,
  totalTaskCount,
  projectId,
}) {
  const [newStage, setNewStage] = useState(false);
  const [stageName, setStageName] = useState("");
  const [editStage, setEditStage] = useState(null);
  const [stagesName, setStagesName] = useState([]);
  const [totalTasks, setTotalTasks] = useState(undefined);
  const [updatedTask, setUpdatedTask] = useState(undefined);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [deleteIndex, setDeleteIndex] = useState(null);
  const [confirmAction, setConfirmAction] = useState(null);

  const handleSave = async () => {
    if (stageName && projectId !== undefined) {
      const response = await createStage(projectId, { name: stageName });

      if (response.status === 201) {

        setStagesName([...stagesName,response.data]);
        setStageName("");
      }

      setNewStage(false);
    }
  };

  useEffect(() => {
    setTotalTasks(totalTaskCount);
  }, [totalTaskCount]);


  const handleNewStage = () => {
    setNewStage(true);
  };

  const handleCancelInput = () => {
    setNewStage(false);
    setStageName("");
  };
  const handleCancelUpdate = () => {
    setEditStage(null);
  };

  const handleEdit = (index) => {
    setEditStage(index);
  };

  const handleDelete = (index) => {

    setShowConfirmModal(true);
    setDeleteIndex(index);
    setConfirmAction("delete");
  };

  const handleConfirmDelete = async () => {
    const response = await deleteStage(stagesName[deleteIndex].id);

    if(response.status === 200){

      setStagesName(stagesName.filter((_, i) => i !== deleteIndex));

    }
    setShowConfirmModal(false);
    setDeleteIndex(null);
    setEditStage(null);
  };

  const handleCancelDelete = () => {
    setShowConfirmModal(false);
    setDeleteIndex(null);
  };

  const handleUpdateStageName = (index, newName) => {
    setUpdatedTask({ name: newName, index: index });
  };

  const handleSaveEdit = () => {
    setShowConfirmModal(true);
    setConfirmAction("edit");
  };

  const handleConfirmEdit = async () => {
    const updatedStages = [...stagesName];

    const response = await updateStage({
      stageId: updatedStages[updatedTask.index].id,
      name: updatedTask.name,
    });

    if (response.status === 200) {
      console.log(response.data);
      updatedStages[updatedTask.index].name = updatedTask.name;
      setStagesName(updatedStages);
    }
    setEditStage(null); // Exit editing mode
    setUpdatedTask(undefined);
    setShowConfirmModal(false);
  };

  const handleUpButton = async (index) => {
    
    const newStages = [...stagesName];

    const response = await handleUpdateSequence({
      stageGoingUp: newStages[index].id, 
      stageGoingDown: newStages[index-1].id
    })

    if(response.status === 200){

      console.log(response.data)
      
      newStages[index] = response.data.newDown //down 
      newStages[index-1] = response.data.newUp; // up

      setStagesName(newStages);


    }
    
    
  }

  const handleDownButton = async (index) => {

    const newStages = [...stagesName];

    const response = await handleUpdateSequence({
      stageGoingUp: newStages[index+1].id, 
      stageGoingDown: newStages[index].id
    });

    if(response.status === 200){

      newStages[index] = response.data.newUp;
      newStages[index+1] = response.data.newDown;
      setStagesName(newStages);

    }

    

  }

  useEffect(() => {
    setStagesName(stages);
  }, [stages]);

  return (
    <section className="project_stages">
      <div className="project_stage_container">
        <div className="stages_container">
          <FaList />
          <h1>Project Stages</h1>
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
              <span onClick={handleCancelInput} className="remove">
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
                  value={
                    updatedTask !== undefined
                      ? updatedTask.name
                      : stagesName[index].name
                  }
                  onChange={(e) => handleUpdateStageName(index, e.target.value)}
                />
              ) : (
                <span className="stage_name">{item.name}</span>
              )}
              
              <div className="last-buttons">
                <div className="up-down-buttons">
                  {index !== 0 ? <span onClick={() => handleUpButton(index)} className="button-up-down"><FaCaretUp /></span> : <></>} 
                  {index !== stagesName.length -1 ? <span onClick={() => handleDownButton(index)} className="button-up-down"> <FaCaretDown /></span> : <></>}
                  
                </div>
                <div className="update_remove">
              
                {editStage === index ? (
                  <span onClick={handleSaveEdit} className="check">
                    <FaCheck />
                  </span>
                ) : (
                  <MdEdit
                    onClick={() => handleEdit(index)}
                    className="edit_stage"
                  />
                )}
                {editStage === index ? (
                  <span onClick={handleCancelUpdate} className="remove">
                    {" "}
                    <AiOutlineClose />
                  </span>
                ) : (
                  <MdDelete
                    onClick={() => handleDelete(index)}
                    className="delete_stage"
                  />
                )}
              </div>
              </div>
              
            </li>
          ))}
        </ul>
      </div>
      <div className="project_tasks_number">
        <div className="project_total_task">
          <label>Total Task</label>
          <span>
            {totalTaskCount !== undefined ? totalTaskCount.totalTaskCount : 0}
          </span>
        </div>
        <div className="project_finished_task">
          <label>Finished Task</label>
          <span>
            {totalTaskCount !== undefined ? totalTaskCount.finishedTask : 0}
          </span>
        </div>
        <div className="project_failed_task">
          <label>Failed Task</label>
          <span>
            {totalTaskCount !== undefined ? totalTaskCount.failedTask : 0}
          </span>
        </div>
      </div>
      {showConfirmModal && confirmAction === "delete" && (
        <WarningModal
          icon={<IoIosWarning />}
          title="Are you sure?"
          paragraph="You won't be able to revert this"
          delete="Delete"
          cancel="Cancel"
          onConfirm={handleConfirmDelete}
          onCancel={handleCancelDelete}
        />
      )}
      {showConfirmModal && confirmAction === "edit" && (
        <WarningModal
          icon={<RiEdit2Fill />}
          title="Are you sure to save the changes?"
          paragraph=""
          delete="Save"
          cancel="Cancel"
          onConfirm={handleConfirmEdit}
          onCancel={() => setShowConfirmModal(false)}
          isEditMode={true}
        />
      )}
    </section>
  );
}
