import React, { useState } from "react";
import { AiOutlineClose } from "react-icons/ai";
import { FaCheck } from "react-icons/fa6";
import { IoMdAdd } from "react-icons/io";
import { MdDelete, MdEdit } from "react-icons/md";
import { FaList } from "react-icons/fa6";

import "./projectStagesDetails.css";

export default function ProjectStagesDetails() {
  const [newStage, setNewStage] = useState(false);
  const [stageName, setStageName] = useState("");
  const [editStage, setEditStage] = useState(null);
  const [stagesName, setStagesName] = useState([
    { fullName: "New" },
    { fullName: "Pending" },
    { fullName: "In Progress" },
    { fullName: "Finished" },
    { fullName: "Failed" },
  ]);

  const handleSave = () => {
    if (stageName) {
      setStagesName([{ fullName: stageName }, ...stagesName]);
      setStageName("");
      setNewStage(false);
    }
  };

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
    const updatedStages = [...stagesName];
    updatedStages[index].fullName = newName;
    setStagesName(updatedStages);
  };
  const handleSaveEdit = () => {
    setEditStage(null); // Exit editing mode
  };

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
                  value={stagesName[index].fullName}
                  onChange={(e) => handleUpdateStageName(index, e.target.value)}
                />
              ) : (
                <span className="stage_name">{item.fullName}</span>
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
          <span>0</span>
        </div>
        <div className="project_finished_task">
          <label>Finished Task</label>
          <span>0</span>
        </div>
        <div className="project_falied_task">
          <label>Failed Task</label>
          <span>0</span>
        </div>
      </div>
    </section>
  );
}
