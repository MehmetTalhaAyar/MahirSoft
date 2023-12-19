import React from "react";

function NavBar({ onLoginClick, isLoginButtonClicked ,onSignUpClick,isSignUpButtonClicked}) {
  return (
    <nav className="nav">
      <a href="#" className="nav_brand">
        Login Page
      </a>
      <ul className="nav_menu">
        <li className="nav_item">
          <a href="#" className="nav_link">
            Home
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            About
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            Skills
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            Services
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            Contacts
          </a>
        </li>
      </ul>
      <div className="navigation-bar-buttons">
      <button
        className={`nav_button ${isSignUpButtonClicked ? "clicked" : ""}`}
        type="submit"
        onClick={onSignUpClick}
      >
        Sign Up
      </button>
      <button
        className={`nav_button ${isLoginButtonClicked ? "clicked" : ""}`}
        type="submit"
        onClick={onLoginClick}
      >
        Log-In
      </button>
      
      </div>
    </nav>
  );
}
export default NavBar;
