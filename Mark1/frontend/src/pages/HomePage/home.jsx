import React, { useState } from "react";
import NavBar from "./NavBar/navbar";
import SideBar from "./sidebar";
import "./home.css";
import { Outlet, useNavigate } from "react-router-dom";
import { useAuthState } from "../../state/context";

function Home() {
  const authState = useAuthState();
  const navigate = useNavigate();

  if (authState.userId <= 0) {
    navigate("/");
  }

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
