import React from "react";
import "./warningModal.css";

export default function WarningModal({
  onConfirm,
  onCancel,
  isEditMode,
  ...prop
}) {
  return (
    <div className="modal-confirm">
      <div className="confirm_container">
        <div className={`warning ${isEditMode ? "edit_warning" : ""}`}>
          {prop.icon}
        </div>
        <h3>{prop.title}</h3>
        <p>{prop.paragraph}</p>
        <div className="btn_container">
          <button
            className={`yes_button ${isEditMode ? "edit_button" : ""}`}
            onClick={onConfirm}
          >
            {prop.delete}
          </button>
          <span className="no_button" onClick={onCancel}>
            {prop.cancel}
          </span>
        </div>
      </div>
    </div>
  );
}
