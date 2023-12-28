import React, { useState } from "react";
import "./myprofile.css";

import { IoLogoLinkedin } from "react-icons/io5";
import { FaInstagramSquare } from "react-icons/fa";
import { FaFacebook } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";
import ChangePasswordPage from "./changepassword";
import EditProfilePage from "./editprofile";
import OverviewPage from "./overview";
function MyProfile() {
  const [currentPage, setCurrentPage] = useState("overview");
  const [profileImage, setProfileImage] = useState(false);
  const [fullName, setFullName] = useState("-");
  const [job, setJob] = useState("-");

  //Change Page function
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  //Change Profile Function
  const handleProfileImage = (newImage) => {
    setProfileImage(newImage);
  };

  //Change Name function
  const handleFullNameChange = (newFullName) => {
    setFullName(newFullName);
  };

  //Change Job function
  const handleJobChange = (newJob) => {
    setJob(newJob);
  };
  return (
    <main>
      <h1>Profile</h1>
      <div>
        <span>Home </span>
        <span>/ Profile</span>
      </div>
      <section className="section">
        <div className="profile_card">
          <img
            src={
              profileImage
                ? URL.createObjectURL(profileImage)
                : "./src/pages/HomePage/ProfilePage/images.jpg"
            }
            alt="Profile"
            className="_image2"
          />
          <h3 className="profile_name">{fullName}</h3>
          <p className="profile_text">{job}</p>
          <div className="logo_img">
            <IoLogoLinkedin />
            <FaInstagramSquare />
            <FaFacebook />
            <FaTwitter />
          </div>
        </div>
        <div className="profile_card_overview">
          <div className="profile_header">
            <div
              className={`overview ${currentPage === "overview" && "active"}`}
              onClick={() => handlePageChange("overview")}
            >
              Overview
            </div>
            <div
              className={`edit_profile ${
                currentPage === "editProfile" && "active"
              }`}
              onClick={() => handlePageChange("editProfile")}
            >
              Edit Profile
            </div>
            <div
              className={`change_password2 ${
                currentPage === "changePassword" && "active"
              }`}
              onClick={() => handlePageChange("changePassword")}
            >
              Change Password
            </div>
          </div>
          <hr></hr>
          {currentPage === "overview" && <OverviewPage />}
          {currentPage === "editProfile" && (
            <EditProfilePage
              onProfileImageChange={handleProfileImage}
              onFullNameChange={handleFullNameChange}
              onJobChange={handleJobChange}
            />
          )}
          {currentPage === "changePassword" && <ChangePasswordPage />}
        </div>
      </section>
    </main>
  );
}
export default MyProfile;
