// Sidebar.js
import "./sidebar.css";

import { useState } from "react";
import { AiOutlineMenu } from "react-icons/ai";
import { MdDashboard } from "react-icons/md";
import { RiTaskLine } from "react-icons/ri";
import { GoProjectSymlink } from "react-icons/go";
import { FaRegCalendarAlt } from "react-icons/fa";
import { LiaEllipsisHSolid } from "react-icons/lia";
import { CiDark } from "react-icons/ci";
import { Link } from "react-router-dom";

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
      <Link className="to-home-link" to={""} style={{textDecoration:'none'}}>
        <div className="sidebar_column">
          <div className="react_img">
            <MdDashboard />
          </div>
          
          <div className="side">Dashboard</div>
         
        </div>
      </Link>
        <div className="sidebar_column ">
          <div className="react_img">
            <RiTaskLine />
          </div>
          <div className="side">Tasks</div>
        </div>
        <div className="sidebar_column">
          <div className="react_img">
            <GoProjectSymlink />
          </div>
          <div className="side">Projects</div>
        </div>
        <div className="sidebar_column">
          <div className="react_img">
            <FaRegCalendarAlt />
          </div>
          <div className="side">Calendar</div>
        </div>
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
