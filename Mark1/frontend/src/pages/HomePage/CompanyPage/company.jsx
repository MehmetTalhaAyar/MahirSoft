import React, { useEffect, useState } from "react";
import { createUser } from "./api";
import "./company.css";
import CompanyRequest from "./CompanyRequest.jsx";
import { IoLogoLinkedin } from "react-icons/io5";
import { FaInstagramSquare, FaFacebook, FaTwitter } from "react-icons/fa";
import { useAuthState } from "../../../state/context";

export function CompanyPage(props) {
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [title, setTitle] = useState("");
  const [gsm, setGsm] = useState("");
  const [errors, setErrors] = useState({});
  const [condition, setCondition] = useState(false);
  const authState = useAuthState();
  const [requestSuccess, setRequestSuccess] = useState(authState.company !== null && authState.company !== undefined ? true : false);

  const saveUser = async (event) => {
    event.preventDefault();
    try {
      const response = await createUser({
        name,
        surname,
        email,
        password,
        title,
        gsm,
      });
      if (response.status === 201) {
        setName("");
        setSurname("");
        setTitle("");
        setGsm("");
        setPassword("");
        setEmail("");
      }
    } catch (axiosError) {
      setErrors(axiosError.response.data.validationErrors);
    }
  };

  useEffect(() => {
    setErrors((lastErrors) => ({ ...lastErrors, name: undefined }));
  }, [name]);

  useEffect(() => {
    setErrors((lastErrors) => ({ ...lastErrors, surname: undefined }));
  }, [surname]);

  useEffect(() => {
    setErrors((lastErrors) => ({ ...lastErrors, title: undefined }));
  }, [title]);

  useEffect(() => {
    setErrors((lastErrors) => ({ ...lastErrors, gsm: undefined }));
  }, [gsm]);

  useEffect(() => {
    setErrors((lastErrors) => ({ ...lastErrors, email: undefined }));
  }, [email]);

  useEffect(() => {
    setErrors((lastErrors) => ({ ...lastErrors, password: undefined }));
  }, [password]);

  useEffect(() => {
    if (authState.userId > 0 && authState.company) {
      setCondition(authState.userId === authState.company.managerId);
    }
  }, [authState]);

  console.log(errors);

  if (!requestSuccess) {
    return <CompanyRequest />;
  }
  else{
    return (
      <main>
        <h1>Company</h1>
        <div className="company_container">
          <div className="left_container">
            <h1 className="company_name">
              {authState.company !== undefined ? authState.company.name : ""}
            </h1>
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
                <a href="#" className="tw">
                  <IoLogoLinkedin className="linkedin" />
                </a>
              </li>
              <li>
                <a href="#" className="tw">
                  <FaInstagramSquare className="instagram" />
                </a>
              </li>
              <li>
                <a href="#" className="tw">
                  <FaFacebook className="facebook" />
                </a>
              </li>
              <li>
                <a href="#" className="tw">
                  <FaTwitter className="twitter" />
                </a>
              </li>
            </ul>
          </div>
          <div className="right_container">
            {condition && (
              <form className="user-form">
                <h2 className="register_form_company">Register Form</h2>
                <input
                  placeholder="Title"
                  className="company_input"
                  onChange={(event) => setTitle(event.target.value)}
                  value={title}
                />
                <input
                  placeholder="Name"
                  className="company_input"
                  onChange={(event) => setName(event.target.value)}
                  value={name}
                />
                <input
                  placeholder="Surname"
                  className="company_input"
                  onChange={(event) => setSurname(event.target.value)}
                  value={surname}
                />
                <input
                  placeholder="Gsm"
                  className="company_input"
                  onChange={(event) => setGsm(event.target.value)}
                  value={gsm}
                />
                <input
                  placeholder="Email"
                  className="company_input"
                  onChange={(event) => setEmail(event.target.value)}
                  value={email}
                />
                <input
                  type="password"
                  placeholder="Password"
                  className="company_input"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                />
                <div className="company_submit_button">
                  <button type="submit" onClick={saveUser}>
                    Submit
                  </button>
                </div>
              </form>
            )}
          </div>
        </div>
      </main>
    );

  }

  
}
