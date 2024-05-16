import { useState } from "react";
import "./projectDetails.css";
import Yetki from "./yetkiler";
import ProjectMembersDetails from "./projectMembersDetails";
import ProjectStagesDetails from "./projectStagesDetails";
import defaultProfileImage from "/src/assets/profileImage.jpg";
import { FaRegEdit } from "react-icons/fa";
import ProjectDetailsDescription from "./projectDetailsDescription";

export default function ProjectDetails() {
  const [editProjectName, setEditProjectName] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleEditProjectName = () => {
    setEditProjectName(true);
  };

  let projectName = "To Do List";
  return (
    <main>
      <div className="project_details_container">
        <div className="project_details">
          {editProjectName ? (
            <form>
              <input
                value={editProjectName}
                onChange={(e) => setEditProjectName(e.target.value)}
                className="change_ProjectName"
                placeholder="Enter New Name"
              />
            </form>
          ) : (
            <>
              <h1>{projectName}</h1>
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
