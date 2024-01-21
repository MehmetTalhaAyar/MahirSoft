import { useState } from "react";
import { CreateUserItem } from "../../../components/CreateUserItems";
import { createUser } from "./api";
import "./company.css";
import { SignFormItem } from "../../../components/SignFormItem";

import { IoLogoLinkedin } from "react-icons/io5";
import { FaInstagramSquare } from "react-icons/fa";
import { FaFacebook } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";

export function CompanyPage(props) {
  const [name, setName] = useState();
  const [surname, setSurname] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [title, setTitle] = useState();
  const [gsm, setGsm] = useState();

  const { companyName } = props;

  const saveUser = () => {
    createUser({
      name,
      surname,
      email,
      password,
      title,
      gsm,
    });
  };

  return (
    <main>
      <h1>Company</h1>
      <div className="company_container">
        <div className="left_container">
          <h1 className="company_name">Company Name</h1>
          <h3 className="company_description">Description:</h3>
          <p className="description">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Praesentium
            odio at provident, distinctio quos iure inventore obcaecati atque,
            odit ea facere cumque. Sit hic cumque doloremque, nihil quod
            adipisci suscipit! Lorem ipsum dolor sit amet consectetur
            adipisicing elit. Illum vitae, dolorem eum suscipit neque, vel nihil
            sunt obcaecati saepe qui fugit repellat nesciunt minus aliquam.
            Error autem asperiores fugit facere!
          </p>

          <h4 className="socialmedia_company">Social Media:</h4>
          <ul>
            <li>
              <a href="https://www.linkedin.com/" className="tw">
                <IoLogoLinkedin className="linkedin" />
              </a>
            </li>
            <li>
              <a href="https://www.instagram.com/" className="tw">
                <FaInstagramSquare className="instagram" />
              </a>
            </li>
            <li>
              <a href="https://www.facebook.com/" className="tw">
                <FaFacebook className="facebook" />
              </a>
            </li>
            <li>
              <a href="https://twitter.com/" className="tw">
                <FaTwitter className="twitter" />
              </a>
            </li>
          </ul>
        </div>
        <div className="right_container">
          <form className="user-form">
            <h2 className="register_form_company">Register Form</h2>
            <input placeholder="Title" className="company_input" />
            <input placeholder="Name" className="company_input" />

            <input placeholder="Surname" className="company_input" />
            <input placeholder="Gsm" className="company_input" />

            <input placeholder="Email" className="company_input" />

            <input
              type="password"
              placeholder="Password"
              className="company_input"
            />

            <div className="company_submit_button">
              <button type="submit">Submit</button>
            </div>
          </form>
        </div>
      </div>
    </main>
  );
}
