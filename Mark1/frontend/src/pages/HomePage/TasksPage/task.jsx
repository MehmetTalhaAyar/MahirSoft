import React, { useEffect, useState } from "react";
import { FaCheck } from "react-icons/fa";
import "./task.css";
import { addTask } from "./api";
import { Link } from "react-router-dom";

function Task(props) {
  // Access props data
  const {
    isNew,
    changeState,
    taskNameSend,
    taskDescriptionSend,
    stageId,
    taskId,

    setActiveCard,
  } = props;
  const [taskName, setTaskName] = useState();
  const [taskDescription, setTaskDescription] = useState();
  const [error, setErrors] = useState();

  const saveElements = async () => {
    try {
      const response = await addTask(stageId, {
        taskName,
        taskDescription,
      });

      if (response.status === 201) {
        changeState(response.data);
      }
    } catch (axiosError) {
      if (axiosError.response.data.status === 400) {
        setErrors(axiosError.response.data.validationErrors);
      }
      console.log(axiosError);
    }
  };

  useEffect(() => {
    if (taskNameSend !== undefined) {
      setTaskName(taskNameSend);
    }
    if (taskDescriptionSend !== undefined) {
      setTaskDescription(taskDescriptionSend);
    }
  }, []);

  if (isNew === true) {
    return (
      <>
        <div
          draggable
          onDragStart={() => setActiveCard(taskId)}
          onDragEnd={() => setActiveCard(null)}
          className="task"
        >
          <button className="save-button" onClick={saveElements}>
            <FaCheck />
          </button>
          <div className="input-container">
            <input
              required
              name="task-name-field"
              maxLength="32"
              onChange={(event) => setTaskName(event.target.value)}
              id="task-name-field"
              type="text"
              className="name-field task-input"
            />
            <label
              className={`task-input-label ${error ? "error" : ""}`}
              htmlFor="task-name-field"
            >
              {error ? error.taskName : "Enter a task name "}
            </label>
          </div>
          <div className="input-container">
            <textarea
              required
              onChange={(event) => {
                setTaskDescription(event.target.value);
              }}
              id="task-desçcription-field"
              name="task-description-field"
              type="text"
              className="description-field task-input"
            />
            <label
              className={`task-input-label ${error ? "error" : ""}`}
              htmlFor="task-description-field"
            >
              {error ? error.taskDescription : "Enter a task description"}
            </label>
          </div>
          <div className="tag">Tag</div>
        </div>
      </>
    );
  } else {
    return (
      <>
        <Link
          className="task_link_decoration"
          to={`/home/task/${taskId}`}
          state={{
            id: taskId,
            name: taskNameSend,
            description: taskDescriptionSend,
          }}
        >
          <div
            draggable
            onDragStart={() => setActiveCard(taskId)}
            onDragEnd={() => setActiveCard(null)}
            className="task"
          >
            <div className="task-name">
              <h1
                className="task-readOnly-input name-field"
                name="task-header"
                id="task-header"
                readOnly={true}
              >
                {taskName}
              </h1>
            </div>
            <div className="task-description">
              <textarea
                name="task-body"
                id="task-body"
                className="task-readOnly-input description-field"
                value={taskDescription}
                readOnly={true}
              />
            </div>
            <div className="tag">Tag</div>
          </div>
        </Link>
      </>
    );
  }
}
export default Task;
