import { useState } from "react";
import "./profile.css";
function Profile() {
  const [isProfileOpen, setIsProfileOpen] = useState(false);

  const openProfile = () => {
    setIsProfileOpen(!isProfileOpen);
  };

  return (
    <>
      <div className={`profile ${isProfileOpen ? "open2" : ""}`}>
        <h1 className="profile_image">Image</h1>
        <div className="profile_container">
          <h3>My Profile</h3>
          <h3>Log Out</h3>
          <div>Settings</div>
        </div>
      </div>
    </>
  );
}
export default Profile;
