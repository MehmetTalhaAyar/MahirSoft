// EditProfilePage.js
import React from "react";
import "./editprofile.css";

import { LuUpload } from "react-icons/lu";
import { MdDeleteOutline } from "react-icons/md";

const EditProfilePage = () => {
  return (
    <div className="edit_profile_page">
      <div className="section2">
        <div className="profile_container2">Profile Image</div>
        <div className="_img">
          <img
            src="./src/pages/HomePage/ProfilePage/images.jpg"
            className="_image3"
          />
          <div className="import_photo">
            <LuUpload className="upload" />
            <MdDeleteOutline className="delete" />
          </div>
        </div>
      </div>
      <div className="section2">
        <div className="full_name">Full Name</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Full Name"
        />
      </div>
      <div className="section2">
        <div className="about_profile">About</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="About"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Company</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Company"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Job</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Job"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Country</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Country"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Adress</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Address"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Phone</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Phone"
        />
      </div>
      <div className="section2">
        <div className="about_profile">Email</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
          placeholder="Email"
        />
      </div>
    </div>
  );
};

export default EditProfilePage;
