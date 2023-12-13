import React from "react";
import "./navbar.css";

import { IoIosLogOut } from "react-icons/io";
import { useState } from "react";

function NavBar() {
  const [isClicked, setIsClicked] = useState(false);

  const handleLogoutClick = () => {
    setIsClicked(true);

    // Set a timeout to reset the scale after 1 second (1000 milliseconds)
    setTimeout(() => {
      setIsClicked(false);
    }, 80);

    // You can perform additional logout logic here if needed
  };
  return (
    <nav>
      <div className="navbar">
        <h1>Home Page</h1>
        <ul className="nav-links">
          <li href="#">Home</li>
          <li href="#">About</li>
          <li href="#">Contact</li>
        </ul>
        <div
          className={`logout_img ${isClicked ? "clicked" : ""}`}
          onClick={handleLogoutClick}
        >
          <IoIosLogOut />
        </div>
      </div>
    </nav>
  );
}
export default NavBar;
