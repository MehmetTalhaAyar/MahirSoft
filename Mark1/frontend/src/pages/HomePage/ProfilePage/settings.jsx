import "./settings.css"

import React, { useState } from "react";


function Settings() {

  const [value, setValue] = useState("light");
  const handleChange = (event) => {
    setValue(event.target.value);
  }

  const [startvalue, start_setValue] = useState("lastVisitPage");
  const start_handleChange = (event) => {
    start_setValue(event.target.value);
  }

  const [langValue, lang_setValue] = useState("en-us")
  const lang_handleChange = (event) => {
    lang_setValue(event.target.value);
  }

  const [notification_Value, notif_setValue] = useState("enable")
  const notif_handleChange = (event) =>{
    notif_setValue(event.target.value);
  }


  return (
    <main>
      <div className="settings-window">
        <div>
          <h1 className="settings-header">
            Settings
          </h1>
          <hr></hr>
        </div>
        <div className="settings-value-area">
          <div className="row-1">
            <h3 className="settings-value">
              Appearance
            </h3>
            <p className="settings-description">
              Customize how looks
            </p>
          </div>

          <div className="drop-down-menu">
            <select className = "select" value={value} onChange={handleChange}>
              <option selected className="selected-option" value="light">Light</option>
              <option selected className="selected-option" value="dark">Dark</option>
            </select>
          </div>
        </div>
        <div className="settings-value-area">
          <div className="row-1">
            <h3 className="settings-value">
              Open on Start
            </h3>
            <p className="settings-description">
              Choose what to show when starts or when you switch workspaces.
            </p>
          </div>
          <div className="drop-down-menu">
            <select className = "select" value={startvalue} onChange={start_handleChange}>
              <option selected className="selected-option"  value="lastVisitPage">Last visited page</option>
              <option selected className="selected-option"  value="homePage">Home Page</option>
            </select>
          </div>
        </div>
        <div className="settings-value-area">
          <div className="row-1">
            <h3 className="settings-value">
              Language
            </h3>
            <p className="settings-description">
              Change the language used in the user interface
            </p>
          </div>

          <div className="drop-down-menu">
            <select className = "select" value={langValue} onChange={lang_handleChange}>
              <option selected className="selected-option" value="en-us">English</option>
              <option selected className="selected-option" value="tr-tr">Türkçe</option>
            </select>
          </div>
        </div>
        <div className="settings-value-area">
          <div className="row-1">
            <h3 className="settings-value">
              Notification
            </h3>
            <p className="settings-description">
              Receive push notifications on mentions via your app
            </p>
          </div>

          <div className="drop-down-menu">
            <select className = "select" value={notification_Value} onChange={notif_handleChange}>
              <option selected className="selected-option" value="enable">Enable</option>
              <option selected className="selected-option" value="disable">Disable</option>
            </select>
          </div>
        </div>
      </div>
    </main>
  );
}
export default Settings;
