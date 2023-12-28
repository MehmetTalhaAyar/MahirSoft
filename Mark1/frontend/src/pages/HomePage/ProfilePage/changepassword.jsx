// ChangePasswordPage.js
import React from "react";
import "./changepassword.css";

function ChangePasswordPage() {
  return (
    <div className="change_password_page">
      <div className="section3">
        <div className="about_profile">Current Password</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
        />
      </div>
      <div className="section3">
        <div className="about_profile">New Password</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
        />
      </div>
      <div className="section3">
        <div className="about_profile">Re-enter New Password</div>
        <input
          type="text"
          className="form-control"
          id="nametext"
          aria-describedby="name"
        />
      </div>
      <div className="end_button">
        <button className="save_changes">Save Password</button>
      </div>
    </div>
  );
}

export default ChangePasswordPage;
