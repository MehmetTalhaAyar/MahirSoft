import React from "react";
import "./dashboard.css";
import Stage from "./stage";

import { IoSettingsOutline } from "react-icons/io5";

function Dashboard({ onFilterClick }) {
  return (
    <div className="wraper">
      <div className="container1">
        <div className="column1">
          <input className="search_bar" type="text" id="fname" name="fname" />
          <button className="filter_button" onClick={onFilterClick}>
            <IoSettingsOutline className="filter_image" fontSize={18} />
            Filter
          </button>
        </div>
      </div>
      <hr></hr>
      <div className="container2">
        <Stage />
      </div>
    </div>
  );
}
export default Dashboard;
