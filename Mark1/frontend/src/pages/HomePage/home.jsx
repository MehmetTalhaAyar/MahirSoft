import React, { useState } from "react";
import NavBar from "./navbar";
import SideBar from "./sidebar";
import "./home.css";
import Dashboard from "./DashboardPage/dashboard";
import MyProfile from "./ProfilePage/myprofile";

function Home() {
  const [showDashboard, setShowDashboard] = useState(true);

  const handleFilterClick = () => {
    setShowDashboard(false);
  };

  const handleProfileLinkClick = () => {
    setShowDashboard(false);
  };

  return (
    <div>
      <NavBar />
      <div className="home_page_wraper">
        <SideBar showProfilePage={handleFilterClick} />
        {showDashboard ? (
          <Dashboard onFilterClick={handleFilterClick} />
        ) : (
          <MyProfile />
        )}
      </div>
    </div>
  );
}

export default Home;
