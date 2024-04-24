import { useState } from "react";
import { useLocation } from "react-router-dom";
import { FaRegEdit } from "react-icons/fa";
import { AiOutlineBars } from "react-icons/ai";

import "./description.css";

export default function Description() {
  const location = useLocation();
  const [isEditing, setIsEditing] = useState(false);
  const [editedTaskDescription, setEditedTaskDescription] = useState(
    location.state.description
  );
  const [taskDescription, setTaskDescription] = useState(
    location.state.description
  );

  const handleEditClick = () => {
    setIsEditing((isEditing) => !isEditing);
  };

  const handleSaveClick = () => {
    setTaskDescription(editedTaskDescription);
    setIsEditing(false);
  };

  const handleCancelClick = () => {
    setEditedTaskDescription(taskDescription);
    setIsEditing(false);
  };

  return (
    <>
      <section className="task_description2">
        <div className="task_description_header">
          <AiOutlineBars />
          <h4 className="task_description_header">Description</h4>
        </div>
        <div className="edit_description">
          <span onClick={handleEditClick}>
            {isEditing ? (
              ""
            ) : (
              <h4 className="edit">
                {" "}
                <FaRegEdit />
                Edit
              </h4>
            )}
          </span>
        </div>
      </section>
      <p className="task_description">
        {isEditing ? (
          <textarea
            placeholder="Update description"
            className="edit_description_input"
            value={editedTaskDescription}
            onChange={(event) => setEditedTaskDescription(event.target.value)}
          />
        ) : (
          <div className="save_description"> • {taskDescription}</div>
        )}
        {isEditing && (
          <span className="save_cancel">
            <button className="save" onClick={() => handleSaveClick()}>
              Save
            </button>
            <span className="cancel" onClick={() => handleCancelClick()}>
              Cancel
            </span>
          </span>
        )}
      </p>
    </>
  );
}