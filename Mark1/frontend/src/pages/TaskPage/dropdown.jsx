import "./dropdown.css";
import { useCallback, useEffect, useState } from "react";
import { HiDotsHorizontal } from "react-icons/hi";
import { useLocation } from "react-router-dom";
import { deleteTaskById } from "./api";

export default function Dropdown() {
  const [dropDownMenu, setDropDownMenu] = useState(false);
  const location = useLocation();

  const deleteTask = async (id) => {
    const response = await deleteTaskById(id);
    if (response.status === 200) {
      window.history.back();
    }
  };
  const openDropdownMenu2 = () => {
    setDropDownMenu(!dropDownMenu);
  };
  return (
    <div>
      <HiDotsHorizontal className="_dot" onClick={() => openDropdownMenu2()} />

      <div
        className={`dropdown-content2 ${dropDownMenu ? "openDropdown2" : " "}`}
      >
        <a onClick={() => deleteTask(location.state.id)}>Delete</a>
        <a href="">Edit</a>
      </div>
    </div>
  );
}
