import React, { useState } from "react";
import "./project.css";
import Select from "react-select";

import { HiDotsHorizontal } from "react-icons/hi";

function Project() {
  const [projectFormOpen, setProjectFormOpen] = useState(false);
  const [projectCards, setProjectCards] = useState([
    { title: "Demo Project", admin: "Admin Name" },
  ]);
  const [titleError, setTitleError] = useState("");
  const [adminError, setAdminError] = useState("");

  const [dropdownStates, setDropdownStates] = useState([false]);
  const [selectedOptions, setSelectedOptions] = useState([]);
  const [projectTitle, setProjectTitle] = useState("");
  const [adminName, setAdminName] = useState("");

  const openProjectForm = () => {
    setProjectFormOpen(!projectFormOpen);
  };

  // Function to add a new project card
  const addProjectCard = () => {
    if (!projectTitle.trim()) {
      console.log("Project Title is required");
      return;
    }

    if (!adminName.trim()) {
      console.log("Admin Name is required");
      return;
    }

    const newProjectCards = [
      ...projectCards,
      { title: projectTitle, admin: adminName },
    ];
    const newDropdownStates = [...dropdownStates, false];
    setProjectCards(newProjectCards);
    setDropdownStates(newDropdownStates);
    // Clear input values after creating the project card
    setProjectTitle("");
    setAdminName("");
    console.log("Project Component created successfully!");
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

  const handleSelectChange = (selectedValues) => {
    setSelectedOptions(selectedValues);
  };

  const options = [
    { value: "Name1", label: "Name 1" },
    { value: "Name2", label: "Name 2" },
    { value: "Name3", label: "Name 3" },
    { value: "Name4", label: "Name 4" },
    { value: "Name5", label: "Name 5" },
    { value: "Name6", label: "Name 6" },
    { value: "Name7", label: "Name 7" },
    { value: "Name8", label: "Name 8" },
    { value: "Name9", label: "Name 9" },
    { value: "Name10", label: "Name 10" },

    // Add more options as needed
  ];

  return (
    <main>
      <h1>My Project</h1>
      <button className="project_button" onClick={openProjectForm}>
        New Project
      </button>

      <div
        className={`project_container ${projectFormOpen ? "isVisible" : ""}`}
      >
        <div className="project_form">
          <input
            type="text"
            placeholder="Project Title"
            className={`project_input ${
              projectFormOpen ? "isVisible_input" : ""
            }`}
            id="titletext"
            value={projectTitle}
            onChange={(e) => {
              setProjectTitle(e.target.value);
              setTitleError("");
            }}
          />
          {titleError && <p className="error-message">{titleError}</p>}
          <input
            type="text"
            placeholder="Admin Name"
            className={`project_input ${
              projectFormOpen ? "isVisible_input" : ""
            }`}
            id="adminName"
            value={adminName}
            onChange={(e) => {
              setAdminName(e.target.value);
              setAdminError("");
            }}
          />
          {adminError && <p className="error-message">{adminError}</p>}

          <Select
            className={`selected_object ${
              projectFormOpen ? "isVisible_select" : ""
            }`}
            options={options}
            isMulti
            value={selectedOptions}
            onChange={handleSelectChange}
          />
        </div>
        <div
          className={`project_form_button ${
            projectFormOpen ? "isVisible_button" : ""
          }`}
        >
          <button className="create_project" onClick={addProjectCard}>
            Create Project
          </button>
        </div>
      </div>
      <div className="project_section">
        {projectCards.map((project, index) => (
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
              <a href="#edit">Edit</a>
            </div>
            <div className="proje_container">
              <h2 className="proje_title">{project.title}</h2>
              <div className="proje_tasks_container">{project.admin}</div>
            </div>
            <span className="created_on">Created on January 01</span>
          </div>
        ))}
      </div>
    </main>
  );
}

export default Project;
