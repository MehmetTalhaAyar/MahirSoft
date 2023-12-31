// Sidebar.js
import "./sidebar.css";
import { SideBarItem } from "../../components/SideBarItems";

import { useState } from "react";
import { AiOutlineMenu } from "react-icons/ai";
import { MdDashboard } from "react-icons/md";
import { RiTaskLine } from "react-icons/ri";
import { GoProjectSymlink } from "react-icons/go";
import { FaRegCalendarAlt } from "react-icons/fa";
import { LiaEllipsisHSolid } from "react-icons/lia";
import { CiDark } from "react-icons/ci";

function SideBar() {
  const [isSideBarOpen, setisSideBarOpen] = useState(false);

  const openSidebar = () => {
    setisSideBarOpen(!isSideBarOpen);
  };

  return (
    <div className={`sidebar ${isSideBarOpen ? "open" : ""}`}>
      <div className="sidebar_icon" onClick={openSidebar}>
        <AiOutlineMenu />
      </div>

      <div className="sidebar_menu">

        <SideBarItem toWhere={"dashboard"} icon={<MdDashboard />} name="Dashboard" />
        <SideBarItem toWhere={"#"} icon={<RiTaskLine />} name="Tasks" />
        <SideBarItem toWhere={"projects"} icon={<GoProjectSymlink />} name="Projects" />
        <SideBarItem toWhere={"#"} icon={<FaRegCalendarAlt />} name="Calendar" />
        
        <div className="sidebar_column">
          <div className="react_img">
            <LiaEllipsisHSolid />
          </div>
          <div className="side">Others</div>
        </div>
      </div>
      <hr className="hr2"></hr>
      <div className="sidebar_column">
        <div className="react_img">
          <CiDark />
        </div>
        <div className="side">Dark Mode</div>
      </div>
    </div>
  );
}
export default SideBar;
