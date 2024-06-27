import React, { useCallback, useEffect, useState } from "react";
import "./dashboard.css";
import Stage from "./stage";

import { IoSettingsOutline } from "react-icons/io5";
import { useLocation } from "react-router-dom";
import { getProjectDetails, updateTaskStage } from "./api";

function Dashboard() {
  const location = useLocation();
  const [stages, setStages] = useState([]);
  const [activeCard, setActiveCard] = useState(null);
  const [activeCardStageId, setActiveCardStageId] = useState(null);

  useEffect(() => {
    getDetails(location.state.projectId);
  }, []);

  const setActiveCardAndStage = (taskId, stageId) => {
    if (taskId !== null && stageId !== null) {
      setActiveCard(taskId);
      setActiveCardStageId(stageId);
    } else {
      setActiveCard(null);
      setActiveCardStageId(null);
    }
  };

  const getDetails = useCallback(async (projectId) => {
    const response = await getProjectDetails(projectId);

    if (response.status === 200) {
      setStages(response.data.stages);
    }
  });

  const onDrop = async (stageId) => {
    console.log(
      `${activeCard} is going to place into ${stageId} and at the position `
    );
    if (activeCard === null || activeCard === undefined) return;

    if (stageId === activeCardStageId) return; // aynı stage e işlem yapılmasını engelliyoruz

    const response = await updateTaskStage({
      taskId: activeCard,
      stageId: stageId,
    });
    if (response.status === 200) {
      const updatedStages = stages.map((stage) => {
        if (stage.id === stageId) {
          stage.tasks.splice(0, 0, response.data)

        } else if (stage.id === activeCardStageId) {

          stage.tasks = stage.tasks.filter((eleman) => {
            if (eleman.id === activeCard) return;
            else return eleman;
          });
        }

        return stage;
      });

      setStages(updatedStages);
      console.log(updatedStages)
    }
    setActiveCard(null);
    setActiveCardStageId(null);
  };

  return (
    <div className="wraper">
      <div className="container1">
        <h1 className="stage">Stage Page</h1>
        {/* <section className="filter_bar">
          <input className="search_bar" type="text" id="fname" name="fname" />
          <button className="filter_button">
            <IoSettingsOutline fontSize={18} />
            Filter
          </button>
        </section> */}
      </div>
      <hr></hr>
      <div className="container2">
        {stages.map((stage) => (
          <Stage
            key={stage.id}
            stage={stage}
            setActiveCard={setActiveCardAndStage}
            onDrop={onDrop}
            activeCard={activeCard}
          />
        ))}
      </div>
    </div>
  );
}
export default Dashboard;
