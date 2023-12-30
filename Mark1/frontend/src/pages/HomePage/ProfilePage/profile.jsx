import "./profile.css";

import { FaRegUser } from "react-icons/fa";
import { FiSettings } from "react-icons/fi";
import { CiLogin } from "react-icons/ci";
import { Link } from "react-router-dom";
import defaultProfileImage from "/src/assets/profileImage.jpg";

function Profile({ isVisible }) {
  if (!isVisible) {
    return null;
  }

  return (
    <div className={`profile ${isVisible ? "visible animate_profile" : ""}`}>
      <div className="profile_image">
        <img
          src={defaultProfileImage}
          className="_image"
        />
        <h3 className="profile_name">Ali Duru</h3>
        <p className="profile_text">FrontEnd Developer</p>
      </div>
      <div className="profile_container">
        
          <Link className="profile_link" to={"myprofile"}>
          <FaRegUser />
          <div className="my_profile">My Profile</div>
          </Link>
        
        <a href="/settings" className="profile_link">
          <FiSettings />
          <div className="settings">Settings</div>
        </a>
      </div>

      <button className="logout_button">
        <CiLogin />
        <div className="log">Logout</div>
      </button>
    </div>
  );
}
export default Profile;
