import React from "react";
import "./navbar.css";
import Profile from "./ProfilePage/profile";

import { CgProfile } from "react-icons/cg";
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
      <div className="homepage_navbar">
        <a href="#" className="home_page_logo">
          Home Page
        </a>
        <ul className="nav-links">
          <li href="#">Home</li>
          <li href="#">About</li>
          <li href="#">Contact</li>
          <Profile />
        </ul>
        <div
          className={`logout_img ${isClicked ? "clicked" : ""}`}
          onClick={handleLogoutClick}
        >
          <CgProfile />
        </div>
      </div>
    </nav>
  );
}
export default NavBar;
