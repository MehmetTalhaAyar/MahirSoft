import React, { useCallback, useEffect, useState } from "react";
import "./projectDetails.css";
import Yetki from "./yetkiler";
import ProjectMembersDetails from "./projectMembersDetails";
import ProjectStagesDetails from "./projectStagesDetails";
import defaultProfileImage from "/src/assets/profileImage.jpg";
import { FaRegEdit } from "react-icons/fa";
import ProjectDetailsDescription from "./projectDetailsDescription";
import { projectDetails, updateProjectName } from "./api";
import { useLocation } from "react-router-dom";

export default function ProjectDetails() {
  const [editProjectName, setEditProjectName] = useState(false);
  const [projectName, setProjectName] = useState("");
  const [projectDescription, setProjectDescription] = useState(
    "Project Description"
  );
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [project, setProject] = useState(undefined);
  const location = useLocation();

  useEffect(() => {
    getProjectDetails();
  }, []);

  const getProjectDetails = useCallback(async () => {
    const response = await projectDetails(location.state.projectId);

    if (response.status === 200) {
      console.log(response.data);

      setProjectName(response.data.name);
      setProjectDescription(response.data.description);
      setProject(response.data);
    }
  });

  const handleEditProjectName = () => {
    setEditProjectName(true);
  };

  const handleProjectNameChange = (e) => {
    setProjectName(e.target.value);
  };

  const handleSaveProjectName = async () => {
    setEditProjectName(false);

    const response = await updateProjectName(location.state.projectId, {
      name: projectName,
    });
    if (response.status === 200) {
      setProjectName(response.data.name);
    }
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
            <ProjectDetailsDescription
              descriptionArrived={projectDescription}
            />

            <div className="project_lider">
              <img
                src={
                  project !== undefined
                    ? `/assets/profile/${project.projectLead.image}`
                    : defaultProfileImage
                }
                className="manager_image_details"
                alt="Manager"
              />
              <h2 className="project_leader_name">
                {project !== undefined
                  ? project.projectLead.fullName
                  : "Leader"}
              </h2>
            </div>
            <ProjectMembersDetails
              members={project !== undefined ? project.projectMembers : []}
              setIsModalOpen={setIsModalOpen}
              projectId ={location.state.projectId}
            />
          </div>
          <ProjectStagesDetails
            projectId={location.state.projectId}
            stages={project !== undefined ? project.projectStages : []}
            totalTaskCount={project !== undefined ? project.taskCounts : {}}
          />
        </div>
        <Yetki isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
      </div>
    </main>
  );
}
