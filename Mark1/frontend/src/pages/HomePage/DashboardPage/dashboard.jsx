import "./dashboard.css";
import Tasks from "./tasks";

function Dashboard() {
  return (
    <div className="wraper">
      <div className="container1">
        <div className="column1">
          <input className="search_bar" type="text" id="fname" name="fname" />
          <button className="buttn">Filter</button>
        </div>
      </div>
      <hr></hr>
      <div className="container2">
        <Tasks />
      </div>
    </div>
  );
}
export default Dashboard;
