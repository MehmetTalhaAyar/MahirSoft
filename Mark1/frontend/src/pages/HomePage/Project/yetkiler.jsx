import React, { useState } from "react";
import "./yetkiler.css";
import { AiOutlineClose } from "react-icons/ai";
import { MdDelete, MdEdit } from "react-icons/md";
import { IoIosWarning } from "react-icons/io";
import WarningModal from "./WarningModal";
import { FaCheck } from "react-icons/fa";

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
    { fullName: "Administrator" },
    { fullName: "Normal User" },
    { fullName: "Company User" },
    { fullName: "Administrator" },
    { fullName: "Normal User" },
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
  const [EditId,setEditId] = useState(null);
  const [checkboxUpdateState,setCheckboxUpdateState] = useState(
    checkboxState
  );

  const handleCheckboxChange = (userIndex, yetkiIndex) => {
    const newCheckboxState = checkboxUpdateState.map((user, i) =>
      i === userIndex
        ? user.map((checked, j) => (j === yetkiIndex ? !checked : checked))
        : user
    );

    setCheckboxUpdateState(newCheckboxState);
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

  const handleEdit = (index) =>{
    setEditId(index);
  }

  const handleCloseEdit = () =>{
    setEditId(null)
    setCheckboxUpdateState(checkboxState)
  }

  const handleSave = (index) => {
    //burada gelen indexin rollerini alacak

    setCheckboxState(checkboxUpdateState);
    setEditId(null);
  }

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

          <table className="yetki-table">
            <thead>
              <tr>
                <th>Role</th>
                {yetkiData.map((item, index) => (
                  <th key={index} className="yetki-header">
                    {item.yetkiName}
                  </th>
                ))}
                <th>Action</th>

              </tr>
            </thead>
            <tbody>

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
              
              {userData.map((user, userIndex) => (
                <tr key={userIndex}>
                  <td className="user-name">{user.fullName}</td>
                  {yetkiData.map((_, yetkiIndex) => (
                    <td key={yetkiIndex} className="yetki-cell">
                      { userIndex !== EditId ?
                        <input
                          type="checkbox"
                          disabled
                          checked={checkboxState[userIndex][yetkiIndex]}
                          onChange={() =>
                            handleCheckboxChange(userIndex, yetkiIndex)
                          }
                        />
                        :
                        <input
                          type="checkbox"
                          checked={checkboxUpdateState[userIndex][yetkiIndex]}
                          onChange={() =>
                            handleCheckboxChange(userIndex, yetkiIndex)
                        }
                      />}
                    </td>
                  ))}
                  <td>
                    { userIndex !== EditId ? 
                    <MdEdit
                    onClick={() => handleEdit(userIndex)}
                    className="edit_yetki"
                    /> :
                      <FaCheck className="save_yetki" onClick={()=> handleSave(userIndex)} />
                    }
                  </td>
                  <td>
                    { userIndex !== EditId ?
                      <MdDelete
                      className="delete_yetki"
                      onClick={() => handleDeleteUser(userIndex)}
                    /> :
                    <AiOutlineClose className="close_yetki" onClick={() => handleCloseEdit()} />
                    }
                  </td>
                </tr>
              ))}
              

              
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
