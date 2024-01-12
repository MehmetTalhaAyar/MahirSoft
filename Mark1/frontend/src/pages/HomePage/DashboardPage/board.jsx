import { useAuthState } from "../../../state/context";
import "./board.css";

function Board() {
  const authState = useAuthState();
  
  return (
    <main>
      <h1>Dashboard Page</h1>
      <div className="dashboard_container">
        <h1 className="dashboard_title">Hi, {authState.fullName}</h1>
        <p className="dashboard_paragraph">
          Check out any upcoming tasks and recent projects below{" "}
        </p>

        {/*<img src="/src/assets/images.jpg" className="dashboard_img" /> */}
      </div>
      <div className="dashboard_cards">
        <div className="dashboard_task">
          <div className="total_tasks">
            <h1>3</h1>
            <span>Total Task</span>
          </div>
          <img src="/src/assets/images.jpg" className="dashboard_img" />
        </div>
        <div className="dashboard_task">
          <div className="total_tasks">
            <h1>2</h1>
            <span>Pending</span>
          </div>
          <img src="/src/assets/images.jpg" className="dashboard_img" />
        </div>
        <div className="dashboard_task">
          <div className="total_tasks">
            <h1>1</h1>
            <span>Due Today</span>
          </div>
          <img src="/src/assets/images.jpg" className="dashboard_img" />
        </div>
      </div>
    </main>
  );
}
export default Board;
