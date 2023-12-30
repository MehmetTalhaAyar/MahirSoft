// EditProfilePage.js
import React, { useState, useRef } from "react";
import "./editprofile.css";
import { LuUpload } from "react-icons/lu";
import { MdDeleteOutline } from "react-icons/md";

function EditProfilePage({
  onProfileImageChange,
  onFullNameChange,
  onJobChange,
  onAboutChange,
  onCompanyChange,
  onCountryChange,
  onAdressChange,
  onPhoneChange,
  onEmailChange,
}) {
  const [profileImage, setProfileImage] = useState(null); // Upload the image on the editpage
  const fileInputRef = useRef(null);
  const [fullName, setFullName] = useState(null);
  const [job, setJob] = useState(null);
  const [about, setAbout] = useState(null);
  const [company, setCompany] = useState(null);
  const [country, setCountry] = useState(null);
  const [adress, setAdress] = useState(null);
  const [phone, setPhone] = useState(null);
  const [email, setEmail] = useState(null);

  // Upload the image function
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setProfileImage(file);
    console.log("Image uploaded:", file);
  };

  const handleUploadClick = () => {
    fileInputRef.current.click();
    console.log("Upload button clicked");
  };

  //Save the image on the Profile Card function
  const saveChanges = () => {
    setProfileImage(onProfileImageChange);
    onFullNameChange(fullName);
    onJobChange(job);
    onAboutChange(about);
    onCompanyChange(company);
    onCountryChange(country);
    onAdressChange(adress);
    onPhoneChange(phone);
    onEmailChange(email);
  };

  const handleFullNameChange = (e) => {
    const newName = e.target.value;
    setFullName(newName);
  };

  const handleJobChange = (newJob) => {
    const newJobName = newJob.target.value;
    setJob(newJobName);
  };

  const handleAboutChange = (about) => {
    const newAbout = about.target.value;
    setAbout(newAbout);
  };

  const handleCompanyChange = (newCompany) => {
    setCompany(newCompany);
  };

  const handleCountryChange = (newCountry) => {
    setCountry(newCountry);
  };

  const handleAdressChange = (newAdress) => {
    setAdress(newAdress);
  };

  const handlePhoneChange = (newPhone) => {
    setPhone(newPhone);
  };

  const handleEmailChange = (newEmail) => {
    setEmail(newEmail);
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
                : "./src/pages/HomePage/ProfilePage/images.jpg"
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
            value={fullName}
            onChange={handleFullNameChange}
          />
        </div>
        <div className="details2">
          <div className="company">Company</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
            value={company}
            onChange={(e) => handleCompanyChange(e.target.value)}
          />
        </div>
        <div className="details2">
          <div className="job">Job</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
            value={job}
            onChange={handleJobChange}
          />
        </div>
        <div className="details2">
          <div className="about">About</div>
          <textarea
            type="text"
            className="textarea-control"
            id="textArea"
            aria-describedby="name"
            value={about}
            onChange={handleAboutChange}
          />
        </div>
        <div className="details2">
          <div className="country">Country</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
            value={country}
            onChange={(e) => handleCountryChange(e.target.value)}
          />
        </div>
        <div className="details2">
          <div className="adress">Adress</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
            value={adress}
            onChange={(e) => handleAdressChange(e.target.value)}
          />
        </div>

        <div className="details2">
          <div className="phone">Phone</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
            value={phone}
            onChange={(e) => handlePhoneChange(e.target.value)}
          />
        </div>
        <div className="details2">
          <div className="email">Email</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
            value={email}
            onChange={(e) => handleEmailChange(e.target.value)}
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
