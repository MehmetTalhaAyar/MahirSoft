import React, { useState, useEffect } from "react";
import { BsTextLeft } from "react-icons/bs";
import { FaRegEdit } from "react-icons/fa";
import "./projectDetailsDescription.css";
import { useLocation } from "react-router-dom";
import { updateDescription } from "../../TaskPage/api";
import { updateProjectDescription } from "./api";

export default function ProjectDetailsDescription(props) {
  const { descriptionArrived } = props;
  const [isEditing, setIsEditing] = useState(false);
  const [description, setDescription] = useState(descriptionArrived);
  const [originalDescription, setOriginalDescription] =
    useState(descriptionArrived);
  const location = useLocation();
  const [project, setProject] = useState({});

  // Set the original description when component mounts
  useEffect(() => {
    setOriginalDescription(description);
  }, []);

  useEffect(() => {
    setOriginalDescription(descriptionArrived);
  }, [descriptionArrived]);

  const handleEditClick = () => {
    setIsEditing(true);
    setDescription(originalDescription);
  };

  const handleSaveClick = async () => {
    setIsEditing(false);

    const response = await updateProjectDescription(location.state.projectId, {
      description: description,
    });
    if (response.status === 200) {
      setOriginalDescription(response.data.description); // Update original description after save
    }
  };

  const handleCancelClick = () => {
    setIsEditing(false);
    setDescription(originalDescription); // Reset description to its original value
  };

  return (
    <div className="description_details">
      <section className="details_description">
        <div className="details_description_header">
          <BsTextLeft />
          <h3>Description</h3>
        </div>
        <div className="edit_description">
          {isEditing ? (
            <div className="save_cancel_buttons">
              <span onClick={handleSaveClick} className="save_button">
                Save
              </span>
              <span onClick={handleCancelClick} className="cancel_button">
                Cancel
              </span>
            </div>
          ) : (
            <span onClick={handleEditClick}>
              <p className="edit">
                <FaRegEdit />
                Edit
              </p>
            </span>
          )}
        </div>
      </section>
      {isEditing ? (
        <textarea
          className="description_textarea"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
      ) : (
        <p className="original_description">
          <pre>{originalDescription}</pre>
        </p>
      )}
    </div>
  );
}
