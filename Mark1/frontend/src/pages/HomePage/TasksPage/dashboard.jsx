import React, { useEffect, useState } from "react";
import "./dashboard.css";
import Stage from "./stage";

import { IoSettingsOutline } from "react-icons/io5";
import { useLocation } from "react-router-dom";

function Dashboard() {
  const location = useLocation();
  const [stages,setStages] = useState([]);


  useEffect(()=>{

    if(location.state){
      setStages(location.state.stages)
    }


    
  },[])

  //bir callback fonksiyonu yazılacak ve proje isminin var olup olmadığına bakılacak.

  return (
    <div className="wraper">
      <div className="container1">
        <div className="column1">
          <input className="search_bar" type="text" id="fname" name="fname" />
          <button className="filter_button">
            <IoSettingsOutline className="filter_image" fontSize={18} />
            Filter
          </button>
        </div>
      </div>
      <hr></hr>
      <div className="container2">
        {
          stages.map((stage)=>(
            <Stage key={stage.id} stageName={stage.name} stageId={stage.id} />
          ))
        }
        
      </div>
    </div>
  );
}
export default Dashboard;
