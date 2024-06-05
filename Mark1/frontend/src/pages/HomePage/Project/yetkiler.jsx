import React, { useState } from "react";
import "./yetkiler.css";
import { AiOutlineClose } from "react-icons/ai";
import { MdDelete } from "react-icons/md";
import { IoIosWarning } from "react-icons/io";
import WarningModal from "./WarningModal";

const yetkiData = [
  { yetkiName: "TASK_CREATE" },
  { yetkiName: "TASK_EDIT" },
  { yetkiName: "TASK_DELETE" },
  { yetkiName: "TASK_CREATE" },
  { yetkiName: "TASK_EDIT" },
  { yetkiName: "TASK_DELETE" },
];

function Yetki({ isOpen, onClose }) {
  const [fullName, setFullName] = useState("");
  const [userData, setUserData] = useState([
    { fullName: "Administrator" },
    { fullName: "Normal User" },
    { fullName: "Company User" },
    { fullName: "Administrator" },
    { fullName: "Normal User" },
  ]);
  const [checkboxState, setCheckboxState] = useState(
    userData.map(() => yetkiData.map(() => false))
  );
  const [showInputRow, setShowInputRow] = useState(false);
  const [deleteUserIndex, setDeleteUserIndex] = useState(null);
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);

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
  };

  const handleAddRow = () => {
    setShowInputRow(true);
  };

  const handleSaveRow = () => {
    if (fullName.trim() !== "") {
      const newUser = { fullName };
      const newRowCheckboxState = yetkiData.map(() => false);

      setUserData([...userData, newUser]);
      setCheckboxState([...checkboxState, newRowCheckboxState]);
      setFullName("");
      setShowInputRow(false);
    }
  };

  const handleCancelRow = () => {
    setShowInputRow(false);
    setFullName("");
  };

  const handleDeleteUser = (index) => {
    setDeleteUserIndex(index);
    setIsConfirmOpen(true);
  };

  const confirmDeleteUser = () => {
    const updatedUserData = userData.filter((_, i) => i !== deleteUserIndex);
    const updatedCheckboxState = checkboxState.filter(
      (_, i) => i !== deleteUserIndex
    );
    setUserData(updatedUserData);
    setCheckboxState(updatedCheckboxState);
    setIsConfirmOpen(false);
    setDeleteUserIndex(null);
  };

  const cancelDeleteUser = () => {
    setIsConfirmOpen(false);
    setDeleteUserIndex(null);
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span onClick={onClose} className="modal-close">
          <AiOutlineClose />
        </span>

        <h2>Yetkiler</h2>
        <div className="yetki-container">
          <table className="yetki-table">
            <thead>
              <tr>
                <th>User</th>
                {yetkiData.map((item, index) => (
                  <th key={index} className="yetki-header">
                    {item.yetkiName}
                  </th>
                ))}
                <th>Action</th>
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
                  <td>
                    <MdDelete
                      className="delete_yetki"
                      onClick={() => handleDeleteUser(userIndex)}
                    />
                  </td>
                </tr>
              ))}
              {showInputRow && (
                <tr>
                  <td>
                    <input
                      type="text"
                      value={fullName}
                      onChange={(e) => setFullName(e.target.value)}
                      placeholder="Enter full name"
                      className="full-name-input"
                    />
                  </td>
                  {yetkiData.map((_, yetkiIndex) => (
                    <td key={yetkiIndex} className="yetki-cell"></td>
                  ))}
                </tr>
              )}

              <div className="add_cancel_container">
                <button
                  onClick={showInputRow ? handleSaveRow : handleAddRow}
                  className="add-button"
                >
                  {showInputRow ? "Save" : "New"}
                </button>
                <span onClick={handleCancelRow} className="cancel_button">
                  {showInputRow ? "Cancel" : ""}
                </span>
              </div>
            </tbody>
          </table>
        </div>

        <button onClick={handleApply} className="apply-button">
          Apply
        </button>
        {isConfirmOpen && (
          <WarningModal
            icon={<IoIosWarning />}
            title="Are you sure?"
            onConfirm={confirmDeleteUser}
            onCancel={cancelDeleteUser}
            paragraph="This user will be deleted"
            delete="Yes, delete"
            cancel="Cancel"
          />
        )}
      </div>
    </div>
  );
}

export default Yetki;
