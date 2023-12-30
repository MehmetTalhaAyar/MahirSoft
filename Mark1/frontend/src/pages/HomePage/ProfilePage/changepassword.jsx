// ChangePasswordPage.js
import React from "react";
import "./changepassword.css";

function ChangePasswordPage() {
  return (
    <div className="change_password_page">
      <div className="password_details2">
        <div className="details2">
          <div className="current_password">Current Password</div>
          <input
            type="text"
            className="password-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="new_password">New Password</div>
          <input
            type="text"
            className="password-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="re-enter_password">Re-enter New Password</div>
          <input
            type="text"
            className="password-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
      </div>
      <div className="end_button">
        <button className="save_changes">Save Password</button>
      </div>
    </div>
  );
}

export default ChangePasswordPage;
