import "./dropdown.css";
import { useState } from "react";
import { HiDotsHorizontal } from "react-icons/hi";
import { useLocation } from "react-router-dom";
import { deleteTaskById } from "./api";
import { FaRegTrashAlt } from "react-icons/fa";
import WarningModal from "../HomePage/Project/WarningModal";
import { IoIosWarning } from "react-icons/io";

export default function Dropdown() {
  const [dropDownMenu, setDropDownMenu] = useState(false);
  const [isConfirmOpen, setIsConfirmOpen] = useState(false); // State to manage modal visibility
  const location = useLocation();

  const openDropdownMenu2 = () => {
    setDropDownMenu(!dropDownMenu);
  };

  const openConfirmModal = () => {
    setIsConfirmOpen(true); // Open the confirmation modal
  };

  const handleConfirmDelete = async () => {
    const response = await deleteTaskById(location.state.id);
    if (response.status === 200) {
      window.history.back();
    }
    setIsConfirmOpen(false); // Close the modal after deletion
  };

  const handleCancelDelete = () => {
    setIsConfirmOpen(false); // Close the modal without deletion
  };

  return (
    <div>
      <HiDotsHorizontal className="_dot" onClick={() => openDropdownMenu2()} />

      <div
        className={`dropdown-content2 ${dropDownMenu ? "openDropdown2" : ""}`}
      >
        <a onClick={openConfirmModal}>
          Delete <FaRegTrashAlt />
        </a>
      </div>

      {isConfirmOpen && (
        <WarningModal
          title="Are you sure?"
          paragraph="You won't be able to revert this"
          delete="Delete"
          cancel="Cancel"
          onConfirm={handleConfirmDelete}
          onCancel={handleCancelDelete}
          icon={<IoIosWarning />}
        />
      )}
    </div>
  );
}
