// OverviewPage.js
import React from "react";
import "./overview.css";

const OverviewPage = () => {
  return (
    <div>
      <div className="about">
        <h2>About</h2>
        <p className="paragraph">
          Sunt est soluta temporibus accusantium neque nam maiores cumque
          temporibus. Tempora libero non est unde veniam est qui dolor. Ut sunt
          iure rerum quae quisquam autem eveniet perspiciatis odit. Fuga sequi
          sed ea saepe at unde.
        </p>
      </div>
      <h2>Profile Details</h2>
      <div className="profile_details">
        <div className="details1">
          <div>Full Name</div>
          <div>Company</div>
          <div>Job</div>
          <div>Country</div>
          <div>Address</div>
          <div>Phone</div>
          <div>Email</div>
        </div>
        <div className="details2">
          <div>Ali Duru</div>
          <div>Mahir Soft</div>
          <div>Frontend Developer</div>
          <div>Turkiye</div>
          <div>Edirne</div>
          <div>+90 536 424 9946</div>
          <div>aliduru@gmail.com</div>
        </div>
      </div>
    </div>
  );
};

export default OverviewPage;
