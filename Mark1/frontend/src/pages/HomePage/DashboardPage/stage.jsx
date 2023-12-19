import "./stage.css";
import Task from "./task";

function Stage() {
  return (
    <div className="scrolling">
      <ul className="cards">
        <li className="card">
          <div className="card_header">
            <div className="header_name">Abdullah Vural</div>
            <div className="new">New+</div>
          </div>
          <div className="scrolling_vertically">
            <Task />
          </div>
        </li>
      </ul>
    </div>
  );
}
export default Stage;
