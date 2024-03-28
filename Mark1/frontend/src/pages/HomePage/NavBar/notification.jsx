import "./notificaiton.css";
import { TfiEmail } from "react-icons/tfi";
import notificationImage from "/src/assets/profileImage.jpg";
import { useAuthState } from "../../../state/context";
import { useState } from "react";
export default function Notification() {
  const [currentPage, setCurrentPage] = useState("notification");

  //Change Page function
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  const authState = useAuthState();
  return (
    <>
      <div className="dropdown_notification animate_profile">
        <header className="header_notification">
          <span
            className={`notification ${
              currentPage === "notification" && "active2"
            }`}
            onClick={() => handlePageChange("notification")}
          >
            Notificaitons
          </span>
          <span
            className={`posta ${currentPage === "posta" && "active2"}`}
            onClick={() => handlePageChange("posta")}
          >
            Email
          </span>
        </header>

        {currentPage === "notification" && (
          <aside className="wraper">
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
            <div className="notification_container">
              <img src={notificationImage} className="notification_image" />
              <div className="notification_message">
                <p className="notification_desc">
                  <b>{authState.fullName}</b> Gelen notification burada gelecek
                </p>
              </div>
            </div>
          </aside>
        )}
        {currentPage === "posta" && (
          <aside className="wraper">
            <div className="email_container">
              {/* Email icon hala düzenlenmedi */}
              <TfiEmail className="email_img" />
              <div className="posta_message">
                <b>{authState.fullName}</b>
                <p className="notification_desc">Gelen email burada gelecek </p>
              </div>
            </div>
          </aside>
        )}
      </div>
    </>
  );
}
