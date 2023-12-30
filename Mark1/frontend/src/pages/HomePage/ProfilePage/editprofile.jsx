// EditProfilePage.js
import React, { useState, useRef } from "react";
import "./editprofile.css";
import { LuUpload } from "react-icons/lu";
import { MdDeleteOutline } from "react-icons/md";
import defaultProfileImage from "/src/assets/profileImage.jpg";


function EditProfilePage({
  onProfileImageChange,
  onFullNameChange,
  onJobChange,
}) {
  const [profileImage, setProfileImage] = useState(null);
  const fileInputRef = useRef(null);
  const [fullName, setFullName] = useState("");
  const [job, setJob] = useState("");

  // Upload the picture
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setProfileImage(file);
    console.log("Image uploaded:", file);
  };

  const handleUploadClick = () => {
    fileInputRef.current.click();
  };

  //Save the picture on the Profile Card
  const saveChanges = () => {
    setProfileImage(onProfileImageChange);
    onFullNameChange(fullName);
    onJobChange(job);
  };

  const handleFullNameChange = (e) => {
    const newName = e.target.value;
    setFullName(newName);
  };

  const handleJobChange = (newJob) => {
    const newJobName = newJob.target.value;
    setJob(newJobName);
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
      <div className="section2">
        <div className="full_name">Full Name</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Full Name"
          value={fullName}
          onChange={handleFullNameChange}
        />
      </div>
      <div className="section2">
        <div className="about_profile">About</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="About"
          value={job}
          onChange={handleJobChange}
        />
      </div>
      <div className="section2">
        <div className="about_profile">Company</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Company"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Job</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Job"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Country</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Country"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Adress</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Address"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Phone</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Phone"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Email</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Email"
        />
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
