import React from "react";
import "./task.css";

function Task(props) {
  // Access props data
  const { taskName } = props;

  return (
    <>
      <div className="task">
        <div>{taskName} : </div>
        <div>Description :</div>
        <div className="tag">Tag</div>
      </div>
    </>
  );
}
export default Task;
