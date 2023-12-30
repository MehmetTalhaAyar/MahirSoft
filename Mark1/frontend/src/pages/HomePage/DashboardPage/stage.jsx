import "./stage.css";
import Task from "./task";
import React, { useState, useEffect } from "react";

function Stage() {
  const [tasks, setTasks] = useState([]);
  const [newTaskCount,setNewTaskCount] = useState(0);

  useEffect(() => {
    // Add a class to trigger the slide-down animation when tasks change
    const scrollingVertically = document.querySelector(".scrolling_vertically");
    scrollingVertically.classList.add("slide-down");

    const animationDuration = 500; // Adjust this value based on your CSS animation duration
    setTimeout(() => {
      scrollingVertically.classList.remove("slide-down");
    }, animationDuration);
  }, [tasks,newTaskCount]);

  //burada tüm taskların elemanlarının isnew alanını false yapıcaz

  const saveTask = () => {
    

    const oldTasks = tasks.map((task)=>{
      if(task.isNew === true){
        task.isNew = false;
      }
    })
    setNewTaskCount(0)

  }

  const loglama = (id) => {

    console.log(id);

  }

  const handleNewButtonClick = () => {
    // Add a new task to the tasks array

    if(newTaskCount === 0){

      const newTask = {
        id: tasks.length,
        isNew: true
      };
      setNewTaskCount(1)
  
      console.log(newTask)
     
      setTasks([newTask,...tasks]);
    }
    else{
      console.log("tek seferde 1 tane görev eklenebilir.")
    }

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
              <Task key={task.id} isNew={task.isNew} onClick={loglama} changeState={saveTask} />
            ))}
          </div>
        </li>
      </ul>
    </div>
  );
}
export default Stage;
