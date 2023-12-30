import React, { useState } from "react";
import "./myprofile.css";

import { IoLogoLinkedin } from "react-icons/io5";
import { FaInstagramSquare } from "react-icons/fa";
import { FaFacebook } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";
import ChangePasswordPage from "./changepassword";
import EditProfilePage from "./editprofile";
import OverviewPage from "./overview";
import defaultProfileImage from "/src/assets/profileImage.jpg";

function MyProfile() {
  const [currentPage, setCurrentPage] = useState("overview"); // Open the Overview page first
  const [profileImage, setProfileImage] = useState(false); // Set the Photo on the Profile Card
  const [fullName, setFullName] = useState("Full Name"); // Set the Full Name to the Profile Card
  const [job, setJob] = useState("Job"); // Set the Job on the Profile Card
  const [about, setAbout] = useState("About Me!!!");
  const [company, setCompany] = useState("Company"); // Set the Company on the Profile Card
  const [country, setCountry] = useState("Country"); // Set the Country on the Profile Card
  const [adress, setAdress] = useState("Adress"); // Set the Adress on the Profile Card
  const [phone, setPhone] = useState("Phone"); // Set the Phone on the Profile Card
  const [email, setEmail] = useState("Email"); // Set the Email on the Profile Card

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

  //Change About function
  const handleAboutChange = (newAbout) => {
    setAbout(newAbout);
  };

  //Change Company function
  const handleCompanyChange = (newCompany) => {
    setCompany(newCompany);
  };

  //Change Country function
  const handleCountryChange = (newCountry) => {
    setCountry(newCountry);
  };

  //Change Adress function
  const handleAdressChange = (newAdress) => {
    setAdress(newAdress);
  };

  //Change Phone function
  const handlePhoneChange = (newPhone) => {
    setPhone(newPhone);
  };

  //Change Email function
  const handleEmailChange = (newEmail) => {
    setEmail(newEmail);
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
                : defaultProfileImage
            }
            alt="Profile"
            className="_image2"
          />
          <h3 className="profile_name">{fullName}</h3>
          <p className="profile_text">{job}</p>
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
              fullName={fullName}
              job={job}
              about={about}
              company={company}
              country={country}
              adress={adress}
              phone={phone}
              email={email}
            />
          )}
          {currentPage === "editProfile" && (
            <EditProfilePage
              onProfileImageChange={handleProfileImage}
              onFullNameChange={handleFullNameChange}
              onJobChange={handleJobChange}
              onAboutChange={handleAboutChange}
              onCompanyChange={handleCompanyChange}
              onCountryChange={handleCountryChange}
              onAdressChange={handleAdressChange}
              onPhoneChange={handlePhoneChange}
              onEmailChange={handleEmailChange}
            />
          )}
          {currentPage === "changePassword" && <ChangePasswordPage />}
        </div>
      </section>
    </main>
  );
}
export default MyProfile;
