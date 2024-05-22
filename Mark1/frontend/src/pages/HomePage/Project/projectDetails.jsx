import React, { useState } from "react";
import "./projectDetails.css";
import Yetki from "./yetkiler";
import ProjectMembersDetails from "./projectMembersDetails";
import ProjectStagesDetails from "./projectStagesDetails";
import defaultProfileImage from "/src/assets/profileImage.jpg";
import { FaRegEdit } from "react-icons/fa";
import ProjectDetailsDescription from "./projectDetailsDescription";

export default function ProjectDetails() {
  const [editProjectName, setEditProjectName] = useState(false);
  const [projectName, setProjectName] = useState("To Do List");
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleEditProjectName = () => {
    setEditProjectName(true);
  };

  const handleProjectNameChange = (e) => {
    setProjectName(e.target.value);
  };

  const handleSaveProjectName = () => {
    setEditProjectName(false);
  };

  return (
    <main>
      <div className="project_details_container">
        <div className="project_details">
          {editProjectName ? (
            <input
              value={projectName}
              onChange={handleProjectNameChange}
              onBlur={handleSaveProjectName}
              className="change_ProjectName"
              placeholder="Enter New Name"
              autoFocus
            />
          ) : (
            <>
              <span>{projectName}</span>
              <FaRegEdit
                onClick={handleEditProjectName}
                className="edit_projectName"
              />
            </>
          )}
        </div>
        <div className="down_container">
          <div className="project_member_stages">
            <ProjectDetailsDescription />

            <div className="project_lider">
              <img
                src={defaultProfileImage}
                className="manager_image_details"
                alt="Manager"
              />
              <h2 className="project_leader_name">Redon</h2>
            </div>
            <ProjectMembersDetails setIsModalOpen={setIsModalOpen} />
          </div>
          <ProjectStagesDetails />
        </div>
        <Yetki isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
      </div>
    </main>
  );
}
