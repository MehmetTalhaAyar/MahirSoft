import React, { useEffect, useState } from "react";
import "./myprofile.css";

import { IoLogoLinkedin } from "react-icons/io5";
import { FaInstagramSquare } from "react-icons/fa";
import { FaFacebook } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";
import ChangePasswordPage from "./changepassword";
import EditProfilePage from "./editprofile";
import OverviewPage from "./overview";
import defaultProfileImage from "/src/assets/profileImage.jpg";
import { useAuthState } from "../../../state/context";

function MyProfile() {
  const [currentPage, setCurrentPage] = useState("overview"); // Open the Overview page first
  const [profileImage, setProfileImage] = useState(false); // Set the Photo on the Profile Card
  const authState = useAuthState();

  //Change Page function
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  useEffect(()=>{
    if(authState.image !== null){

      setProfileImage(authState.image);
    }
  },[])

  //Change Profile Function
  const handleProfileImage = (newImage) => {
    setProfileImage(newImage);
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
                ? `/assets/profile/${profileImage}`
                : defaultProfileImage
            }
            alt="Profile"
            className="_image2"
          />
          <h3 className="profile_name">{authState.fullName}</h3>
          <p className="profile_text">{authState.title}</p>
          <div className="logo_img">
            <IoLogoLinkedin className="linkedin" />
            <FaInstagramSquare className="instagram" />
            <FaFacebook className="facebook" />
            <FaTwitter className="twitter" />
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
          {currentPage === "overview" && (
            <OverviewPage
              fullName={authState.fullName}
              job={authState.title}
              company={
                authState.company !== undefined && authState.company !== null
                  ? authState.company.name
                  : ""
              }
              phone={authState.gsm}
              email={authState.email}
            />
          )}
          {currentPage === "editProfile" && (
            <EditProfilePage onProfileImageChange={handleProfileImage} />
          )}
          {currentPage === "changePassword" && <ChangePasswordPage />}
        </div>
      </section>
    </main>
  );
}
export default MyProfile;
