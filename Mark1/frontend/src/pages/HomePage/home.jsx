import React, { useState } from "react";
import NavBar from "./navbar";
import SideBar from "./sidebar";
import "./home.css";
import { Outlet } from "react-router-dom";

function Home() {
  
  return (
    <div>
      <NavBar />
      <div className="home_page_wraper">
        <SideBar />

        <Outlet />
        
      </div>
    </div>
  );
}

export default Home;
