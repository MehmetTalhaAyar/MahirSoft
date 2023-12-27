import "./profile.css";

import { FaRegUser } from "react-icons/fa";
import { FiSettings } from "react-icons/fi";
import { CiLogin } from "react-icons/ci";

function Profile({ isVisible }) {
  if (!isVisible) {
    return null;
  }

  return (
    <div className={`profile ${isVisible ? "visible animate_profile" : ""}`}>
      <div className="profile_image">
        <img
          src="./src/pages/HomePage/ProfilePage/images.jpg"
          className="_image"
        />
        <h3 className="profile_name">Ali Duru</h3>
        <p className="profile_text">FrontEnd Developer</p>
      </div>
      <div className="profile_container">
        <a href="/myprofile" className="profile_link">
          <FaRegUser />
          <div className="my_profile">My Profile</div>
        </a>
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
