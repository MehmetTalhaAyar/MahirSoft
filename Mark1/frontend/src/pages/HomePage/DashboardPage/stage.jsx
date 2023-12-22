import "./stage.css";
import Task from "./task";
import React, { useState } from "react";

function Stage() {
  const [tasks, setTasks] = useState([]);

  const handleNewButtonClick = () => {
    // Add a new task to the tasks array
    setTasks([...tasks, <Task key={tasks.length} taskName="Redon" />]);
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

          <div className="scrolling_vertically">{tasks}</div>
        </li>
      </ul>
    </div>
  );
}
export default Stage;
