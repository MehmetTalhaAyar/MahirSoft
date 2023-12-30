// OverviewPage.js
import React from "react";
import "./overview.css";

function OverviewPage({
  fullName,
  job,
  about,
  company,
  country,
  adress,
  phone,
  email,
}) {
  return (
    <div className="overview_page">
      <div className="about_overview">
        <h2>About</h2>
        <p className="paragraph">{about}</p>
      </div>
      <h2>Profile Details</h2>
      <div className="profile_details">
        <div className="details">
          <div className="full_name">Full Name</div>
          <div className="full_name_change">{fullName}</div>
        </div>
        <div className="details">
          <div className="company">Company</div>
          <div className="company_change">{company}</div>
        </div>
        <div className="details">
          <div className="job">Job</div>
          <div className="job_change">{job}</div>
        </div>
        <div className="details">
          <div className="country">Country</div>
          <div className="country_change">{country}</div>
        </div>
        <div className="details">
          <div className="adress">Address</div>
          <div className="adress_change">{adress}</div>
        </div>
        <div className="details">
          <div className="phone">Phone</div>
          <div className="phone_change">{phone}</div>
        </div>
        <div className="details">
          <div className="email">Email</div>
          <div className="email_change">{email}</div>
        </div>
      </div>
    </div>
  );
}

export default OverviewPage;
