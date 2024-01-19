import React, { useCallback, useEffect, useState } from "react";
import "./project.css";
import Select from "react-select";

import { HiDotsHorizontal } from "react-icons/hi";
import { createProject, getCompanyMembers,getCompanyProjects,deleteProjectById} from "./api";
import { MONTHS } from "../../../Constants/Constants";
import { Link } from "react-router-dom";

function Project() {
  const [projectFormOpen, setProjectFormOpen] = useState(false);
  const [projectCards, setProjectCards] = useState([]);
  const [titleError, setTitleError] = useState("");
  const [adminError, setAdminError] = useState("");

  const [dropdownStates, setDropdownStates] = useState([false]);
  const [selectedOptions, setSelectedOptions] = useState([]);
  const [projectTitle, setProjectTitle] = useState("");
  const [adminName, setAdminName] = useState([]);
  const [options,setOptions] = useState("");

  // console.log(adminName.label)
  // console.log()

  const getMembers = useCallback( async ()=>{
    
    const response = await getCompanyMembers();

    if(response.status === 200 && response.data){
      setOptions(response.data.map((user)=>{
        return {
          value:user.userId,
          label:user.fullName
        }
      }))
    }
    

  },[])

  const getProjects = useCallback( async () => {
    const response = await getCompanyProjects();

    if(response.status === 200 && response.data ){
      setProjectCards(response.data.map((project)=>{
        return {
          id: project.id,
          title: project.name,
          admin: project.leadingPerson.fullName,
          adminId: project.leadingPerson.userId,
          stages: project.stages,
          createdOn: {
            day: project.createdOn.split('T')[0].split('-')[2],
            month: project.createdOn.split('T')[0].split('-')[1],
            year :project.createdOn.split('T')[0].split('-')[0]
          }
        }
      }))

    }

  },[])

  useEffect(()=>{
    getMembers()
    getProjects()
  },[])

  useEffect(()=>{

  },[projectCards])

  const openProjectForm = () => {
    setProjectFormOpen(!projectFormOpen);
  };

  // Function to add a new project card
  const addProjectCard = async () => {
    if (!projectTitle.trim()) {
      console.log("Project Title is required");
      return;
    }

    if (!adminName.label.trim()) {
      console.log("Admin Name is required");
      return;
    }

    try {
      const response = await createProject({
        project:{
          name: projectTitle
        },
        adminId: adminName.value,
        projectUserIds: selectedOptions.map((eleman)=>{
          return eleman.value;
        })
      })

      console.log(response)

      if(response.status === 201){

        const newProjectCards = [
          ...projectCards,
          {
            id:response.data.id,
            title: response.data.name,
            admin: response.data.leadingPerson.fullName,
            adminId: response.data.leadingPerson.userId,
            stages: response.data.stages,
            createdOn: {
              day: response.data.createdOn.split('T')[0].split('-')[2],
              month: response.data.createdOn.split('T')[0].split('-')[1],
              year :response.data.createdOn.split('T')[0].split('-')[0]
            }
          },
        ];
        const newDropdownStates = [...dropdownStates, false];
        setProjectCards(newProjectCards);
        setDropdownStates(newDropdownStates);

      }

    } catch (error) {
      setTitleError("Project title size must be between 2 and 32 characters!")
      
    }

    
    // Clear input values after creating the project card
    setProjectTitle("");
    setAdminName([]);
    setSelectedOptions([])
    console.log("Project Component created successfully!");
  };

  // Function to open dropdown menu
  const openDropdownMenu = (index) => {
    const newDropdownStates = [...dropdownStates];
    newDropdownStates[index] = !newDropdownStates[index];
    setDropdownStates(newDropdownStates);
  };

  //Function to delete a project card
  const deleteProject = async (index) => {



    const response = await deleteProjectById(index);
  
    if(response.status === 200){

      const myProjectCard = [...projectCards];
      const deleteobjectindex = projectCards.findIndex((project)=> project.id === index)

      myProjectCard.splice(deleteobjectindex, 1);
      const myDropdownMenu = [...dropdownStates];
      myDropdownMenu.splice(deleteobjectindex, 1);


  
      setProjectCards(myProjectCard);
      setDropdownStates(myDropdownMenu);
    }

    
  };

  const handleSelectChange = (selectedValues) => {
    setSelectedOptions(selectedValues);
  };

  const handleAdminChange = (selectedValue) => {
    setAdminName(selectedValue)
  }



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
          <Select
            placeholder="Project Manager"
            className={`project_input ${
              projectFormOpen ? "isVisible_select" : ""
            }`}
            isClearable="true"
            value={adminName}
            options={options}
            onChange={handleAdminChange}
          />
          {adminError && <p className="error-message">{adminError}</p>}

          <Select
            className={`selected_object ${
              projectFormOpen ? "isVisible_select" : ""
            }`}
            placeholder="Project Members"
            options={options}
            isMulti
            closeMenuOnSelect={false}
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
          <div key={index} className="project_card">
            <div>
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
              <a href="#delete" onClick={() => deleteProject(project.id)}>
                Delete
              </a>
              <a href="#edit">Edit</a>
            </div>
          </div>
          <Link className="to-links" to={project.title.toLowerCase()} state={{admin:project.admin,stages:project.stages}}>
            <div className="proje_container">
              <h2 className="proje_title">{project.title}</h2>
              <div className="proje_tasks_container">{project.admin}</div>
            </div>
            <span className="created_on">Created on {`${project.createdOn.day} ${MONTHS[project.createdOn.month]} ${project.createdOn.year}`}</span>
            

          </Link>
          </div>
        ))}
      </div>
    </main>
  );
}

export default Project;
