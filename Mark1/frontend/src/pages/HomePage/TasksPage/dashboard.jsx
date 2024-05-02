import React, { useEffect, useState } from "react";
import "./dashboard.css";
import Stage from "./stage";

import { IoSettingsOutline } from "react-icons/io5";
import { useLocation } from "react-router-dom";

function Dashboard() {
  const location = useLocation();
  const [stages, setStages] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [activeCard, setActiveCard] = useState(null);

  useEffect(() => {
    if (location.state) {
      setStages(location.state.stages);
    }
  }, []);

  const onDrop = (stage, position) => {
    console.log(
      `${activeCard} is going to place into ${stage} and at the position ${position}`
    );
    if (activeCard === null || activeCard === undefined) return;
    const taskToMove = tasks[activeCard]; // hatalı
    const updatedTasks = tasks.filter((task, index) => index !== activeCard); // hatalı

    updatedTasks.splice(stage.name, 0, {
      ...taskToMove,
      status: stage.name,
    });
    setTasks(updatedTasks);
  };

  return (
    <div className="wraper">
      <div className="container1">
        <h1 className="stage">Stage Page</h1>
        <section className="filter_bar">
          <input className="search_bar" type="text" id="fname" name="fname" />
          <button className="filter_button">
            <IoSettingsOutline fontSize={18} />
            Filter
          </button>
        </section>
      </div>
      <hr></hr>
      <div className="container2">
        {stages.map((stage) => (
          <Stage
            key={stage.id}
            stageName={stage.name}
            stageId={stage.id}
            setActiveCard={setActiveCard}
            onDrop={onDrop}
            updateTasks={updateAllTasks}
            setTasks={setTasks}
          />
        ))}
      </div>
      <h1>Task = {activeCard}</h1>
    </div>
  );
}
export default Dashboard;
