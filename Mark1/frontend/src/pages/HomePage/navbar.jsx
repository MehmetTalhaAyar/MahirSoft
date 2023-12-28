import "./navbar.css";
import Profile from "./ProfilePage/profile";
import React, { useState } from "react";

import { CgProfile } from "react-icons/cg";

function NavBar() {
  const [isProfileVisible, setIsProfileVisible] = useState(false);

  const handleProfileClick = () => {
    setIsProfileVisible(!isProfileVisible);
  };

  /*const [isClicked, setIsClicked] = useState(false);*/

  /*
  const handleLogoutClick = () => {
    setIsClicked(true);

    // Set a timeout to reset the scale after 1 second (1000 milliseconds)
    setTimeout(() => {
      setIsClicked(false);
    }, 80);
  };
  */
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
          className="logout_img"
          onClick={handleProfileClick}

          /* className={`logout_img ${isClicked ? "clicked" : ""}`}
          onClick={handleLogoutClick}*/
        >
          <CgProfile />
        </div>
        {isProfileVisible && <Profile isVisible={isProfileVisible} />}
      </div>
    </nav>
  );
}
export default NavBar;
