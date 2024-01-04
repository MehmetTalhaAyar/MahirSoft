import React, { useState } from "react";
import "./project.css";

import { HiDotsHorizontal } from "react-icons/hi";

function Project() {
  const [projectCards, setProjectCards] = useState(["Demo Project"]);
  const [dropdownStates, setDropdownStates] = useState([false]);

  // Function to add a new project card
  const addProjectCard = () => {
    const newProjectCards = [...projectCards, "New Project"];
    const newDropdownStates = [...dropdownStates, false];
    setProjectCards(newProjectCards);
    setDropdownStates(newDropdownStates);
  };

  // Function to open dropdown menu
  const openDropdownMenu = (index) => {
    const newDropdownStates = [...dropdownStates];
    newDropdownStates[index] = !newDropdownStates[index];
    setDropdownStates(newDropdownStates);
  };

  //Function to delete a project card
  const deleteProject = (index) => {
    const myProjectCard = [...projectCards];
    myProjectCard.splice(index, 1);
    const myDropdownMenu = [...dropdownStates];
    myDropdownMenu.splice(index, 1);

    setProjectCards(myProjectCard);
    setDropdownStates(myDropdownMenu);
  };

  return (
    <main>
      <h1>My Project</h1>
      <button className="project_button" onClick={addProjectCard}>
        New Project
      </button>

      <div className="project_section">
        {projectCards.map((title, index) => (
          <div className="project_card" key={index}>
            <HiDotsHorizontal
              className="_dot"
              onClick={() => openDropdownMenu(index)}
            />

            <div
              id={`myDropdown${index}`}
              className={`dropdown-content ${
                dropdownStates[index] ? "openDropdown" : ""
              }`}
            >
              <a href="#delete" onClick={() => deleteProject(index)}>
                Delete
              </a>
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
    </main>
  );
}

export default Project;
