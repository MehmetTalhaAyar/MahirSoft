// EditProfilePage.js
import React, { useState, useRef } from "react";
import "./editprofile.css";
import { LuUpload } from "react-icons/lu";
import { MdDeleteOutline } from "react-icons/md";
import defaultProfileImage from "/src/assets/profileImage.jpg";

function EditProfilePage({ onProfileImageChange }) {
  const [profileImage, setProfileImage] = useState(null); // Upload the image on the editpage
  const fileInputRef = useRef(null);

  // Upload the image function
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setProfileImage(file);
  };

  const handleUploadClick = () => {
    fileInputRef.current.click();
    console.log("Upload button clicked");
  };

  //Save the image on the Profile Card function
  const saveChanges = () => {
    onProfileImageChange(profileImage);
    setProfileImage(false);
  };

  return (
    <div className="edit_profile_page">
      <div className="section2">
        <div className="profile_container2">Profile Image</div>
        <div className="_img">
          <img
            src={
              profileImage
                ? URL.createObjectURL(profileImage)
                : defaultProfileImage
            }
            alt="Profile"
            className="_image3"
          />
          <div className="import_photo">
            <LuUpload className="upload" onClick={handleUploadClick} />
            <MdDeleteOutline
              className="delete"
              onClick={() => setProfileImage(null)}
            />
            <input
              type="file"
              ref={fileInputRef}
              accept="image/*"
              onChange={handleImageChange}
              style={{ display: "none" }}
            />
          </div>
        </div>
      </div>
      <div className="profile_details2">
        <div className="details2">
          <div className="full_name">Full Name</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="company">Company</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="job">Job</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="about">About</div>
          <textarea
            type="text"
            className="textarea-control"
            id="textArea"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="country">Country</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="adress">Adress</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>

        <div className="details2">
          <div className="phone">Phone</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="email">Email</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
      </div>
      <div className="end_button">
        <button className="save_changes" onClick={saveChanges}>
          Save Changes
        </button>
      </div>
    </div>
  );
}

export default EditProfilePage;
