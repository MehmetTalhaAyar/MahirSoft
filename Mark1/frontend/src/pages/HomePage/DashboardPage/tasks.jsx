import "./tasks.css";
import { CiCirclePlus } from "react-icons/ci";
function Tasks() {
  return (
    <div className="scrolling">
      <div className="plus">
        <CiCirclePlus />
      </div>
      <ul className="cards">
        <li className="card">
          <div className="container3">
            <h4>New +</h4>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
          </div>
        </li>
        <li className="card">
          <div className="container3">
            <h4>New</h4>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
          </div>
        </li>
        <li className="card">
          <div className="container3">
            <h4>New</h4>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
          </div>
        </li>
        <li className="card">
          <div className="container3">
            <h4>New</h4>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
          </div>
        </li>

        <li className="card">
          <div className="container3">
            <h4>New</h4>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
          </div>
        </li>
        <li className="card">
          <div className="container3">
            <h4>New</h4>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
            <div className="tasks">Task</div>
          </div>
        </li>
      </ul>
    </div>
  );
}
export default Tasks;
