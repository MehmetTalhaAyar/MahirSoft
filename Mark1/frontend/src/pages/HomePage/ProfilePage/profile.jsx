import "./profile.css";

import { FaRegUser } from "react-icons/fa";
import { FiSettings } from "react-icons/fi";
import { CiLogin } from "react-icons/ci";
import { Link, useNavigate } from "react-router-dom";
import defaultProfileImage from "/src/assets/profileImage.jpg";
import { useAuthDispatch, useAuthState } from "../../../state/context";

function Profile({ isVisible }) {
  const authState = useAuthState();
  const dispatch = useAuthDispatch();
  const navigate = useNavigate();

  if (!isVisible) {
    return null;
  }

  const onclick = () => {
    dispatch({ type: "logout-success" });
    navigate("/");
  };

  return (
    <div className={`profile ${isVisible ? "visible animate_profile" : ""}`}>
      <div className="profile_image">
        <img src={defaultProfileImage} className="_image" />
        <h3 className="profile_name">{authState.fullName}</h3>
        <p className="profile_text">{authState.title}</p>
      </div>
      <div className="profile_container">
        <Link className="profile_link" to={"myprofile"}>
          <FaRegUser />
          <div className="my_profile">My Profile</div>
        </Link>

        <Link className="profile_link" to={"settings"}>
          <FiSettings />
          <div className="settings">Settings</div>
        </Link>
      </div>

      <button className="logout_button" onClick={onclick}>
        <CiLogin />
        <div className="log">Logout</div>
      </button>
    </div>
  );
}
export default Profile;
