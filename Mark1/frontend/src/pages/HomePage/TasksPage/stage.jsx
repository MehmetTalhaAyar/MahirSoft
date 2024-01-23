import { Link } from "react-router-dom";
import { getTasks } from "./api";
import "./stage.css";
import Task from "./task";
import React, { useState, useEffect, useCallback } from "react";

function Stage(props) {
  const { stageName, stageId } = props;
  const [tasks, setTasks] = useState([]);
  const [newTaskCount, setNewTaskCount] = useState(0);

  useEffect(() => {
    // Add a class to trigger the slide-down animation when tasks change
    const scrollingVertically = document.querySelector(
      `.${stageName.replace(" ", "-")}`
    );
    scrollingVertically.classList.add("slide-down");

    const animationDuration = 500; // Adjust this value based on your CSS animation duration
    setTimeout(() => {
      scrollingVertically.classList.remove("slide-down");
    }, animationDuration);
  }, [tasks, newTaskCount]);

  const getTask = useCallback(async () => {
    const response = await getTasks(stageId);

    if (response.status === 200) {
      setTasks(response.data.tasks);
    }
  }, []);

  useEffect(() => {
    getTask();
  }, []);

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

      console.log(newTask);

      setTasks([newTask, ...tasks]);
    } else {
      console.log("tek seferde 1 tane görev eklenebilir.");
    }
  };
  return (
    <ul className="cards">
      <li className="card">
        <div className="card_header">
          <div className="header_name">{stageName}</div>
          <button className="new" onClick={handleNewButtonClick}>
            New
          </button>
        </div>

        <div className={`scrolling_vertically ${stageName.replace(" ", "-")}`}>
          {tasks.map((task) => (
            <Task
              key={task.id}
              isNew={task.isNew ? task.isNew : false}
              taskNameSend={task.name}
              taskDescriptionSend={task.description}
              changeState={saveTask}
              stageId={stageId}
              taskId={task.id}
            />
          ))}
        </div>
      </li>
    </ul>
  );
}
export default Stage;
