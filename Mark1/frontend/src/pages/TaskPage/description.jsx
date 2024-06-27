import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { FaRegEdit } from "react-icons/fa";
import { AiOutlineBars } from "react-icons/ai";

import "./description.css";
import { updateDescription } from "./api";

export default function Description(props) {
  const location = useLocation();
  const [isEditing, setIsEditing] = useState(false);
  const [editedTaskDescription, setEditedTaskDescription] = useState(
    props.description
  );
  const [taskDescription, setTaskDescription] = useState(props.description);

  const handleEditClick = () => {
    setIsEditing((isEditing) => !isEditing);
  };

  useEffect(() => {
    if (props.taskDescription !== undefined) {
      setTaskDescription(props.taskDescription);
    }
  }, []);

  const handleSaveClick = async () => {
    const response = await updateDescription({
      taskId: location.state.id,
      description: editedTaskDescription,
    });

    if (response.status === 200) {
      setTaskDescription(editedTaskDescription);
    }

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
              <p className="edit">
                {" "}
                <FaRegEdit />
                Edit
              </p>
            )}
          </span>
        </div>
      </section>
      <div className="task_description">
        {isEditing ? (
          <textarea
            placeholder="Update description"
            className="edit_description_input"
            value={editedTaskDescription}
            onChange={(event) => setEditedTaskDescription(event.target.value)}
          />
        ) : (
          <div className="save_description">
            <pre>{taskDescription}</pre>
          </div>
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
      </div>
    </>
  );
}
