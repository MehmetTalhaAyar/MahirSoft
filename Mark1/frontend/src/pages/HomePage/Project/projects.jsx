import React, { useCallback, useEffect, useState } from "react";
import "./project.css";
import AsyncSelect from "react-select/async";

import { CiSearch } from "react-icons/ci";
import { HiDotsHorizontal } from "react-icons/hi";
import { FaChevronDown } from "react-icons/fa";
import { FaChevronUp } from "react-icons/fa";
import { IoIosArrowForward } from "react-icons/io";
import {
  createProject,
  getCompanyMembers,
  getCompanyProjects,
  deleteProjectById,
} from "./api";
import { MONTHS } from "../../../Constants/Constants";
import { Link } from "react-router-dom";
import defaultProfileImage from "../../../assets/profileImage.jpg";

function Project() {
  const [projectFormOpen, setProjectFormOpen] = useState(false);
  const [projectCards, setProjectCards] = useState([]);
  const [titleError, setTitleError] = useState("");
  const [adminError, setAdminError] = useState("");

  const [dropdownStates, setDropdownStates] = useState([false]);
  const [selectedOptions, setSelectedOptions] = useState([]);
  const [projectTitle, setProjectTitle] = useState("");
  const [adminName, setAdminName] = useState([]);
  const [options, setOptions] = useState("");

  const [filterInput, setFilterInput] = useState("");
  const [filteredProjectCards, setFilteredProjectCards] = useState([]);

  // console.log(adminName.label)
  // console.log()

  const getMembers = useCallback(async () => {
    const response = await getCompanyMembers({ searchKey: "" });

    if (response.status === 200 && response.data) {
      setOptions(
        response.data.map((user) => {
          return {
            value: user.userId,
            label: user.fullName,
          };
        })
      );
    }
  }, []);

  const getProjects = useCallback(async () => {
    const response = await getCompanyProjects();

    if (response.status === 200 && response.data) {
      setProjectCards(
        response.data.map((project) => {
          return {
            id: project.id,
            title: project.name,
            admin: project.leadingPerson.fullName,
            adminId: project.leadingPerson.userId,
            stages: project.stages,
            createdOn: {
              day: project.createdOn[2],
              month: project.createdOn[1],
              year: project.createdOn[0],
            },
          };
        })
      );
    }
  }, []);

  useEffect(() => {
    getMembers();
    getProjects();
  }, []);

  useEffect(() => {
    filterProjects(); // Call filterProjects whenever projectCards or filterInput changes
  }, [projectCards, filterInput]);

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
        project: {
          name: projectTitle,
        },
        adminId: adminName.value,
        projectUserIds: selectedOptions.map((eleman) => {
          return eleman.value;
        }),
      });

      console.log(response);

      if (response.status === 201) {
        const newProjectCards = [
          ...projectCards,
          {
            id: response.data.id,
            title: response.data.name,
            admin: response.data.leadingPerson.fullName,
            adminId: response.data.leadingPerson.userId,
            stages: response.data.stages,
            createdOn: {
              day: response.data.createdOn[2],
              month: response.data.createdOn[1],
              year: response.data.createdOn[0],
            },
          },
        ];
        const newDropdownStates = [...dropdownStates, false];
        setProjectCards(newProjectCards);
        setDropdownStates(newDropdownStates);
      }
    } catch (error) {
      setTitleError("Project title size must be between 2 and 32 characters!");
    }

    // Clear input values after creating the project card
    setProjectTitle("");
    setAdminName([]);
    setSelectedOptions([]);
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

    if (response.status === 200) {
      const myProjectCard = [...projectCards];
      const deleteobjectindex = projectCards.findIndex(
        (project) => project.id === index
      );

      myProjectCard.splice(deleteobjectindex, 1);
      const myDropdownMenu = [...dropdownStates];
      myDropdownMenu.splice(deleteobjectindex, 1);

      setProjectCards(myProjectCard);
      setDropdownStates(myDropdownMenu);
    }
  };

  const handleSelectChange = (selectedValues) => {
    setSelectedOptions(selectedValues);
    console.log(selectedValues);
  };

  const handleAdminChange = (selectedValue) => {
    // console.log(selectedValue)
    setAdminName(selectedValue);
  };

  const filterMembers = (inputValue) => {
    return inputValue.map((user) => {
      return {
        value: user.userId,
        label: user.fullName,
      };
    });
  };

  const promiseOptions = async (inputValue) => {
    const response = await getCompanyMembers({
      searchKey: inputValue,
    });
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(filterMembers(response.data));
      }, 100);
    });
  };

  // Function to handle filter input change
  const handleFilterInput = (event) => {
    setFilterInput(event.target.value);
  };

  // Function to filter project cards based on input value
  const filterProjects = () => {
    const filteredProjects = projectCards.filter((project) =>
      project.title.toLowerCase().includes(filterInput.toLowerCase())
    );
    setFilteredProjectCards(filteredProjects);
  };

  const members = [
    { id: 1, imageSrc: defaultProfileImage },
    { id: 2, imageSrc: defaultProfileImage },
    { id: 3, imageSrc: defaultProfileImage },
    { id: 4, imageSrc: defaultProfileImage },
  ];
  return (
    <main>
      <h1>My Project</h1>
      <div className="button_filter">
        <button className="project_button" onClick={openProjectForm}>
          New Project
          {projectFormOpen ? <FaChevronUp /> : <FaChevronDown />}
        </button>
        <div className="search_wraper">
          <CiSearch className="search_icon" />
          <input
            type="text"
            value={filterInput}
            placeholder="Search"
            className="filter_bar_project"
            onChange={handleFilterInput}
          />
        </div>
      </div>
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
          <AsyncSelect
            placeholder="Project Manager"
            className={`project_input ${
              projectFormOpen ? "isVisible_select" : ""
            }`}
            isClearable="true"
            cacheOptions
            defaultOptions={options}
            value={adminName}
            onChange={handleAdminChange}
            loadOptions={promiseOptions}
          />
          {adminError && <p className="error-message">{adminError}</p>}
          <AsyncSelect
            className={`selected_object ${
              projectFormOpen ? "isVisible_select" : ""
            }`}
            placeholder="Project Members"
            cacheOptions
            defaultOptions={options}
            isMulti
            loadOptions={promiseOptions}
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

      <section className="project_wraper">
        <div className="project_section">
          {filteredProjectCards.map((project, index) => (
            <div key={index} className="project_card">
              {/* <Link to={"/home/projectDetailss"}> */}
              <header className="project_header">
                <h2 className="proje_title">{project.title}</h2>
                <HiDotsHorizontal
                  className={`${dropdownStates[index] ? "_dot2" : "_dot"}`}
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
              </header>
              {/* </Link> */}

              <div className="proje_container">
                <section className="manager_side">
                  <p className="manager_header">Manager</p>
                  <div className="proje_manager_container">
                    <img
                      src={defaultProfileImage}
                      alt="Manager Image"
                      className="manager_image"
                    />
                  </div>
                  <span className="manager_name">{project.admin}</span>
                </section>
                <section className="member_side">
                  <p className="member_header">Members</p>
                  <ul className="proje_member_list">
                    {members.map((member, index) => {
                      if (index >= 6) {
                        return (
                          <li key={member.id} className="plus_7">
                            +{index - 5 >= 9 ? "9" : index - 5}
                          </li>
                        );
                      }

                      return (
                        <li key={member.id} className="member_list">
                          <img
                            src={member.imageSrc}
                            alt="Manager Image"
                            className="member_image"
                          />
                        </li>
                      );
                    })}
                  </ul>

                  <div className="task_link_button">
                    <Link
                      className="to-links"
                      to={project.title.toLowerCase()}
                      state={{ admin: project.admin, stages: project.stages }}
                    >
                      <button className="go_into_tasks">
                        Go to Tasks <IoIosArrowForward />
                      </button>
                    </Link>
                  </div>
                </section>
              </div>

              <span className="created_on">
                Created on
                {` ${project.createdOn.day} ${
                  MONTHS[project.createdOn.month]
                } ${project.createdOn.year}`}
              </span>
            </div>
          ))}
        </div>
      </section>
    </main>
  );
}

export default Project;
