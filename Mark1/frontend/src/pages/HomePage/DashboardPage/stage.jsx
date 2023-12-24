import "./stage.css";
import Task from "./task";
import React, { useState, useEffect } from "react";

function Stage() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    // Add a class to trigger the slide-down animation when tasks change
    const scrollingVertically = document.querySelector(".scrolling_vertically");
    scrollingVertically.classList.add("slide-down");

    const animationDuration = 500; // Adjust this value based on your CSS animation duration
    setTimeout(() => {
      scrollingVertically.classList.remove("slide-down");
    }, animationDuration);
  }, [tasks]);

  const handleNewButtonClick = () => {
    // Add a new task to the tasks array
    const newTask = {
      id: 0,
      taskName: "Redon",
    };

    const updatedTasks = tasks.map((task) => ({
      ...task,
      id: task.id + 1,
    }));

    console.log("New Task:", newTask);
    console.log("All Tasks Before:", tasks);
    setTasks([newTask, ...updatedTasks]);
    console.log("All Tasks After:", [newTask, ...updatedTasks]);
  };
  return (
    <div className="scrolling">
      <ul className="cards">
        <li className="card">
          <div className="card_header">
            <div className="header_name">Ali Duru</div>
            <button className="new" onClick={handleNewButtonClick}>
              New
            </button>
          </div>

          <div className="scrolling_vertically">
            {tasks.map((task) => (
              <Task key={task.id} taskName={task.taskName} />
            ))}
          </div>
        </li>
      </ul>
    </div>
  );
}
export default Stage;
