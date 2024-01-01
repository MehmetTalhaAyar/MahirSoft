import React, { useState } from "react";
import "./project.css";

import { HiDotsHorizontal } from "react-icons/hi";

function Project() {
  // State to track the project cards
  const [projectCards, setProjectCards] = useState(["Project Title"]);
  const [isDropdown, setIsOpenDropdown] = useState(false);

  // Function to add a new project card
  const addProjectCard = () => {
    const newProjectCards = [...projectCards, "New Project"];
    setProjectCards(newProjectCards);
  };

  const openDropdownMenu = () => {
    setIsOpenDropdown(!isDropdown);
  };

  return (
    <main>
      <h1>My Project</h1>
      <button className="project_button" onClick={addProjectCard}>
        New Project
      </button>

      <div className="project_section">
        {/* Map through projectCards array to render project cards */}
        {projectCards.map((title, index) => (
          <div className="project_card" key={index}>
            <HiDotsHorizontal className="_dot" onClick={openDropdownMenu} />

            <div
              id="myDropdown"
              className={`dropdown-content ${isDropdown ? "openDropdown" : ""}`}
            >
              <a href="#delete">Delete</a>
            </div>
            <div className="proje_container">
              <h2 className="proje_title">{title}</h2>
              <div className="proje_tasks_container">
                <div className="proje_task">Task</div>
                <div className="proje_task">Task</div>
                <div className="proje_task">Task</div>
              </div>
            </div>
            <span className="created_on">Created on January 01</span>
          </div>
        ))}
      </div>
      {/* Button to add a new project card */}
    </main>
  );
}

export default Project;
