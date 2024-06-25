import React, { useEffect, useState } from "react";
import "./yetkiler.css";
import { AiOutlineClose } from "react-icons/ai";
import { MdDelete, MdEdit } from "react-icons/md";
import { IoIosWarning } from "react-icons/io";
import WarningModal from "./WarningModal";
import { FaCheck } from "react-icons/fa";
import { createRole, getAuthorization, updateRole } from "./api";



function Yetki({ isOpen, onClose }) {
  const [fullName, setFullName] = useState("");
  const [userData, setUserData] = useState([]);
  const [authData,setAuthData] = useState([]);
  const [checkboxState, setCheckboxState] = useState(
    userData.map(() => authData.map(() => false))
  );
  const [showInputRow, setShowInputRow] = useState(false);
  const [deleteUserIndex, setDeleteUserIndex] = useState(null);
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [EditId,setEditId] = useState(null);
  const [checkboxUpdateState,setCheckboxUpdateState] = useState(
    checkboxState
  );
  const [userAuths,setUserAuths] = useState();

  const handleCheckboxChange = (userIndex, authIndex) => {
    const newCheckboxState = checkboxUpdateState.map((user, i) =>
      i === userIndex
        ? user.map((checked, j) => (j === authIndex ? !checked : checked))
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

  useEffect(()=>{
    getAuthorizationData();
  },[])

  useEffect(()=>{
    let newCheckBoxState = userData.map(() => authData.map(() => false));
    
    userData.map((user,userIndex)=>{
      authData.map((auth,authIndex)=>{
        if(userAuths[user.userRoleId].length !== 0 && userAuths[user.userRoleId].includes(auth.authorizationId)){
          return newCheckBoxState[userIndex][authIndex] = true;
        }else {
          return newCheckBoxState[userIndex][authIndex] = false;
          
        }
        
      });
    });

    setCheckboxState(newCheckBoxState);
    setCheckboxUpdateState(newCheckBoxState);
},[userAuths])

  const getAuthorizationData = async () =>{
    const response = await getAuthorization();

    if(response.status === 200){
      setAuthData(response.data.authorizations)
      setUserData(response.data.userRoles)
      setUserAuths(response.data.userRoleAuths)

      
      
    }

  }

  const handleSaveRow = async() => {
    if (fullName.trim() !== "") {
      const newRowCheckboxState = authData.map(() => false);

      const response = await createRole({name:fullName})
      if(response.status === 201){

        console.log(response.data)

        setUserData([...userData, response.data]);
        setCheckboxState([...checkboxState, newRowCheckboxState]);
        setCheckboxUpdateState([...checkboxUpdateState , newRowCheckboxState]);
      }

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

  const handleSave = async (index) => {
    //burada gelen indexin rollerini alacak
    
    let data = authData.map((auth,authIndex)=>{
      if(checkboxUpdateState[index][authIndex]){
        return auth.authorizationId
      }
      
    }).filter((auth) => auth ?? auth)

    const response = await updateRole({
      userRoleId: userData[index].userRoleId,
      authorityNumbers: data
    })

    if(response.status === 200){

      setCheckboxState(checkboxUpdateState);
    }
    else{
      setCheckboxUpdateState(checkboxState);
    }
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
                <th >Role</th>
                {authData.map((item, index) => (
                  <th key={index} className="yetki-header">
                    {item.definition}
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
                  {authData.map((_, yetkiIndex) => (
                    <td key={yetkiIndex} className="yetki-cell"></td>
                  ))}
                </tr>
              )}
              
              {userData.map((user, userIndex) => (
                <tr key={userIndex}>
                  <td className="user-name">{user.name}</td>
                  {authData.length !== 0 ? authData.map((_, authIndex) => (
                    <td key={authIndex} className="yetki-cell">
                      { userIndex !== EditId ?
                        <input
                          type="checkbox"
                          disabled
                          checked={checkboxState[userIndex][authIndex]}
                        />
                        :
                        <input
                          type="checkbox"
                          checked={checkboxUpdateState[userIndex][authIndex]}
                          onChange={() =>
                            handleCheckboxChange(userIndex, authIndex)
                        }
                      />}
                    </td>
                  )) : <></>}
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
