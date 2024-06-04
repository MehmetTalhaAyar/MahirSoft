import { Link } from "react-router-dom";
import "./navbar.css";
import Profile from "../ProfilePage/profile";
import React, { useState } from "react";
import { CgProfile } from "react-icons/cg";

import { BiMessageSquareDetail } from "react-icons/bi";
import Notification from "./notification";

function NavBar() {
  const [isProfileVisible, setIsProfileVisible] = useState(false);
  const [isNotificationVisible, setIsNotificationVisible] = useState(false);

  const handleProfileClick = () => {
    setIsProfileVisible(!isProfileVisible);
    setIsNotificationVisible(false);
  };

  const handleNotificationClick = () => {
    setIsNotificationVisible(!isNotificationVisible);
    setIsProfileVisible(false);
  };

  return (
    <nav>
      <div className="homepage_navbar">
        <Link to={""} className="home_page_logo">
          MahirSoft
        </Link>

        <div className="message_profile_icon">
          <div className="notification_icone" onClick={handleNotificationClick}>
            <BiMessageSquareDetail />
          </div>
          <div className="logout_img" onClick={handleProfileClick}>
            <CgProfile />
          </div>
        </div>
        {isProfileVisible && <Profile isVisible={isProfileVisible} />}
        {isNotificationVisible && <Notification />}
      </div>
    </nav>
  );
}
export default NavBar;
