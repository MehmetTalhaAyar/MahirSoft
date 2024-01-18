import { Link } from "react-router-dom";
import { getTasks } from "./api";
import "./stage.css";
import Task from "./task";
import React, { useState, useEffect, useCallback } from "react";


function Stage(props) {
  const { stageName,stageId } = props;
  const [tasks, setTasks] = useState([]);
  const [newTaskCount, setNewTaskCount] = useState(0);

  useEffect(() => {
    // Add a class to trigger the slide-down animation when tasks change
    const scrollingVertically = document.querySelector(".scrolling_vertically");
    scrollingVertically.classList.add("slide-down");

    const animationDuration = 500; // Adjust this value based on your CSS animation duration
    setTimeout(() => {
      scrollingVertically.classList.remove("slide-down");
    }, animationDuration);
  }, [tasks, newTaskCount]);

  const getTask = useCallback(async () => {
    const response = await getTasks(stageId);
    console.log(response.data.tasks);

    if (response.status === 200) {
      setTasks(response.data.tasks);
    }
  }, []);

  useEffect(() => {
    getTask();
  }, []);

  const saveTask = () => {
    const oldTasks = tasks.map((task) => {
      if (task.isNew === true) {
        task.isNew = false;
      }
    });
    setNewTaskCount(0);
  };

  const handleNewButtonClick = () => {
    // Add a new task to the tasks array

    if (newTaskCount === 0) {
      const newTask = {
        taskId: tasks.length + 1,
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
    <div className="scrolling">
      <ul className="cards">
        <li className="card">
          <div className="card_header">
            <div className="header_name">{stageName}</div>
            <button className="new" onClick={handleNewButtonClick}>
              New
            </button>
          </div>

          <div className={`scrolling_vertically ${stageName}`}>
            {tasks.map((task) => (
              <Link to={`/home/task/${task.id}`} state={{id:task.id , name:task.name,description:task.description}} >
              <Task
                key={task.id}
                isNew={task.isNew ? task.isNew : false}
                taskNameSend={task.name}
                taskDescriptionSend={task.description}
                changeState={saveTask}
                stageId={stageId}
              />
              </Link>
            ))}
          </div>
        </li>
      </ul>
    </div>
  );
}
export default Stage;
