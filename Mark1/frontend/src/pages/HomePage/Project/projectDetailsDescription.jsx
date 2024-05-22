import React, { useState, useEffect } from "react";
import { BsTextLeft } from "react-icons/bs";
import { FaRegEdit } from "react-icons/fa";
import "./projectDetailsDescription.css";

export default function ProjectDetailsDescription() {
  const [isEditing, setIsEditing] = useState(false);
  const [description, setDescription] = useState("No description provided.");
  const [originalDescription, setOriginalDescription] = useState("");

  // Set the original description when component mounts
  useEffect(() => {
    setOriginalDescription(description);
  }, []);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleSaveClick = () => {
    setIsEditing(false);
    setOriginalDescription(description); // Update original description after save
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
              <h4 className="edit">
                <FaRegEdit />
                Edit
              </h4>
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
        <p>{description}</p>
      )}
    </div>
  );
}
