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

  const notificaiton = [
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vural",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vural",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vural",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vural",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen notification burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vural",
      description: "Gelen notification burada gelecek",
    },
  ];
  const email = [
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vurall",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vurall",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Redon Çapuni",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Mehmet Tahla",
      description: "Gelen email burada gelecek",
    },
    {
      imageSrc: notificationImage,
      name: "Abdullah Vurall",
      description: "Gelen email burada gelecek",
    },
  ];
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
          <aside className="notification_wraper">
            {notificaiton.map((member) => {
              return (
                <li className="notification_container" key={notificaiton.name}>
                  <img src={member.imageSrc} className="notification_image" />
                  <div className="notification_message">
                    <p className="notification_desc">
                      <b>{member.name}</b>&nbsp;
                      {member.description}
                    </p>
                  </div>
                </li>
              );
            })}
          </aside>
        )}
        {currentPage === "posta" && (
          <aside className="posta_wraper">
            {email.map((member) => {
              return (
                <li className="email_container" key={member.name}>
                  <TfiEmail className="email_img" />
                  <div className="posta_message">
                    <b>{member.name}</b>
                    <p className="posta_desc">Gelen email burada gelecek </p>
                  </div>
                </li>
              );
            })}
          </aside>
        )}
      </div>
    </>
  );
}
