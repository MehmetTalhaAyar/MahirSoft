// ChangePasswordPage.js
import React from "react";
import "./changepassword.css";

const ChangePasswordPage = () => {
  return (
    <div className="change_profile_page">
      <div className="section3">
        <div className="about_profile">Current Password</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
        />
      </div>
      <div className="section3">
        <div className="about_profile">New Password</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
        />
      </div>
      <div className="section3">
        <div className="about_profile">Re-enter New Password</div>
        <input
          type="text"
          class="form-control"
          id="nametext"
          aria-describedby="name"
        />
      </div>
    </div>
  );
};

export default ChangePasswordPage;
