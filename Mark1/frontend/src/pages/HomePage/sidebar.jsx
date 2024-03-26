// Sidebar.js
import "./sidebar.css";
import { SideBarItem } from "../../components/SideBarItems";

import { useState } from "react";
import { AiOutlineMenu } from "react-icons/ai";
import { MdDashboard } from "react-icons/md";
import { RiTaskLine } from "react-icons/ri";
import { FaTasks } from "react-icons/fa";
import { GoProjectSymlink } from "react-icons/go";
import { FaRegCalendarAlt, FaUsers } from "react-icons/fa";
import { LiaEllipsisHSolid } from "react-icons/lia";
import { useAuthState } from "../../state/context";

function SideBar() {
  const [isSideBarOpen, setisSideBarOpen] = useState(false);
  const authState = useAuthState();

  const openSidebar = () => {
    setisSideBarOpen(!isSideBarOpen);
  };

  return (
    <div className={`sidebar ${isSideBarOpen ? "open" : ""}`}>
      <div className="sidebar_icon" onClick={openSidebar}>
        <AiOutlineMenu />
      </div>

      <div className="sidebar_menu">
        <SideBarItem toWhere={""} icon={<MdDashboard />} name="Dashboard" />
        {/* <SideBarItem toWhere={"#"} icon={<RiTaskLine />} name="Tasks" /> */}
        <SideBarItem
          toWhere={"projects"}
          icon={<GoProjectSymlink />}
          name="Projects"
        />

        {authState.company && (
          <SideBarItem toWhere={"company"} icon={<FaUsers />} name="Company" />
        )}
      </div>
      <hr className="hr2"></hr>
      <SideBarItem toWhere={"#"} icon={<LiaEllipsisHSolid />} name="Others" />
    </div>
  );
}
export default SideBar;
