import "./dashboard.css";
import Stage from "./stage";

import { IoSettingsOutline } from "react-icons/io5";

function Dashboard() {
  return (
    <div className="wraper">
      <div className="container1">
        <div className="column1">
          <input className="search_bar" type="text" id="fname" name="fname" />
          <button className="filter_button">
            <IoSettingsOutline className="filter_image" />
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
