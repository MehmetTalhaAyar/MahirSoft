import "./stage.css";
import Task from "./task";
import { IoIosAdd } from "react-icons/io";

import React, { useState, useEffect, useRef } from "react";
import DropArea from "./dropArea";

function Stage(props) {
  const { stage, activeCard, setActiveCard, onDrop } = props;

  const [tasks, setTasks] = useState([]);
  const [newTaskCount, setNewTaskCount] = useState(0);
  const [showDrop, setShowDrop] = useState(false);
  const myRef = useRef(null);

  useEffect(() => {
    // Add a class to trigger the slide-down animation when tasks change
    const scrollingVertically = document.querySelector(
      `.${stage.name.replace(" ", "-")}`
    );
    scrollingVertically.classList.add("slide-down");

    setNewTaskCount(0);

    const animationDuration = 500; // Adjust this value based on your CSS animation duration
    setTimeout(() => {
      scrollingVertically.classList.remove("slide-down");
    }, animationDuration);
  }, [tasks, newTaskCount]);

  useEffect(() => {
    setTasks(stage.tasks);
  }, [stage.tasks.length]);

  useEffect(() => {
    if (activeCard === null) {
      const stageCards = document.querySelectorAll(".card");
      stageCards.forEach((card) => {
        card.classList.remove("card_draggable");
      });
    } else {
      const stageCards = document.querySelectorAll(".card");
      stageCards.forEach((card) => {
        card.classList.add("card_draggable");
      });
    }
  }, [activeCard]);

  useEffect(() => {
    if (showDrop) {
      myRef.current.classList.add("card_on_draggable");
    } else {
      myRef.current.classList.remove("card_on_draggable");
    }
  }, [showDrop]);

  const saveTask = (response) => {
    const oldTasks = tasks.map((task) => {
      if (task.isNew === true) {
        task.id = response.id;
        task.name = response.name;
        task.description = response.description;
        task.isNew = false;
      }
    });
    setNewTaskCount(0);
  };

  const handleNewButtonClick = () => {
    // Add a new task to the tasks array

    if (newTaskCount === 0) {
      const newTask = {
        id: tasks.length + 1,
        isNew: true,
      };
      setNewTaskCount(1);

      setTasks([newTask, ...tasks]);
    } else {
      console.log("tek seferde 1 tane görev eklenebilir.");
    }
  };

  return (
    <ul className="cards">
      <li
        onDragEnter={() => setShowDrop(true)}
        onDragLeave={() => setShowDrop(false)}
        onDrop={() => {
          onDrop(stage.id);
          setShowDrop(false);
        }}
        onDragOver={(e) => e.preventDefault()}
        className="card"
        ref={myRef}
      >
        <div className="card_header">
          <div className="header_name">{stage.name}</div>
          <button className="new" onClick={handleNewButtonClick}>
            <IoIosAdd />
          </button>
        </div>

        <div className={`scrolling_vertically ${stage.name.replace(" ", "-")}`}>
          {tasks.map((task) => (
            <Task
              key={task.id}
              isNew={task.isNew ? task.isNew : false}
              taskNameSend={task.name}
              taskDescriptionSend={task.description}
              changeState={saveTask}
              stageId={stage.id}
              taskId={task.id}
              setActiveCard={setActiveCard}
              setShowDrop={setShowDrop}
            />
          ))}
        </div>
      </li>
    </ul>
  );
}
export default Stage;
