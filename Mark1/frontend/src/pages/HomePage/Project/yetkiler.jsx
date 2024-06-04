// Modal.js
import React, { useState } from "react";
import "./yetkiler.css";
import { AiOutlineClose } from "react-icons/ai";

const yetkiData = [
  { yetkiName: "TASK_CREATE" },
  { yetkiName: "TASK_EDIT" },
  { yetkiName: "TASK_DELETE" },
  { yetkiName: "TASK_CREATE" },
  { yetkiName: "TASK_EDIT" },
  { yetkiName: "TASK_DELETE" },
  { yetkiName: "TASK_CREATE" },
  { yetkiName: "TASK_EDIT" },
  { yetkiName: "TASK_DELETE" },
  { yetkiName: "TASK_CREATE" },
  { yetkiName: "TASK_EDIT" },
];

const userData = [
  { fullName: "Administrator" },
  { fullName: "Normal User" },
  { fullName: "Company User" },
  { fullName: "Administrator" },
  { fullName: "Normal User" },
  { fullName: "Company User" },
];

const Yetki = ({ isOpen, onClose }) => {
  const [checkboxState, setCheckboxState] = useState(
    userData.map(() => yetkiData.map(() => false))
  );

  if (!isOpen) return null;

  const handleCheckboxChange = (userIndex, yetkiIndex) => {
    const newCheckboxState = checkboxState.map((user, i) =>
      i === userIndex
        ? user.map((checked, j) => (j === yetkiIndex ? !checked : checked))
        : user
    );
    setCheckboxState(newCheckboxState);
  };

  const handleApply = () => {
    console.log("Applied permissions:", checkboxState);
    // Implement the logic to save the permissions
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span onClick={onClose} className="modal-close">
          <AiOutlineClose />
        </span>
        <h2>Yetkiler</h2>
        <div className="table-container">
          <table className="yetki-table">
            <thead>
              <tr>
                <th>User</th>
                {yetkiData.map((item, index) => (
                  <th key={index} className="yetki-header">
                    {item.yetkiName}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {userData.map((user, userIndex) => (
                <tr key={userIndex}>
                  <td className="user-name">{user.fullName}</td>
                  {yetkiData.map((_, yetkiIndex) => (
                    <td key={yetkiIndex} className="yetki-cell">
                      <input
                        type="checkbox"
                        checked={checkboxState[userIndex][yetkiIndex]}
                        onChange={() =>
                          handleCheckboxChange(userIndex, yetkiIndex)
                        }
                      />
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <button onClick={handleApply} className="apply-button">
          Apply
        </button>
      </div>
    </div>
  );
};

export default Yetki;
