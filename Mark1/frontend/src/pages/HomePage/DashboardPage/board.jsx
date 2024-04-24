import { useCallback, useEffect, useState } from "react";
import { useAuthState } from "../../../state/context";
import "./board.css";
import { getResponsibleTaskCount } from "./api";

function Board() {
  const authState = useAuthState();
  const [totalTaskCount, setTotalTaskCount] = useState(0);
  const [pendingTaskCount, setPendingTaskCount] = useState(0);
  const [dueTaskCount, setDueTaskCount] = useState(0);

  useEffect(() => {
    getTaskCounts();
  }, []);

  const getTaskCounts = useCallback(async () => {
    const response = await getResponsibleTaskCount();

    if (response.status === 200) {
      setTotalTaskCount(response.data.totalTaskCount);
      setPendingTaskCount(response.data.pendingTaskCount);
      setDueTaskCount(response.data.dueTaskCount);
    }
  }, []);

  return (
    <main>
      <h1 className="dashboard_header">Dashboard Page</h1>
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
            <h1>{totalTaskCount}</h1>
            <span className="total">
              <p className="square"></p>Total Task
            </span>
          </div>
          <img src="/src/assets/images.jpg" className="dashboard_img" />
        </div>
        <div className="dashboard_task">
          <div className="total_tasks">
            <h1>{pendingTaskCount}</h1>
            <span className="total">
              <p className="square"></p>Pending
            </span>
          </div>
          <img src="/src/assets/images.jpg" className="dashboard_img" />
        </div>
        <div className="dashboard_task">
          <div className="total_tasks">
            <h1>{dueTaskCount}</h1>
            <span className="total">
              <p className="square"></p>Due Today
            </span>
          </div>
          <img src="/src/assets/images.jpg" className="dashboard_img" />
        </div>
      </div>
    </main>
  );
}
export default Board;
