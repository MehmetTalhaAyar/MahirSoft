import { useEffect, useState } from "react";
import { CreateUserItem } from "../../../components/CreateUserItems";
import { createUser } from "./api";
import "./company.css";
import { SignFormItem } from "../../../components/SignFormItem";

import { IoLogoLinkedin } from "react-icons/io5";
import { FaInstagramSquare } from "react-icons/fa";
import { FaFacebook } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";
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
      // console.log(axiosError)
      setErrors(axiosError.response.data.validationErrors);
    }
  };

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        name: undefined,
      };
    });
  }, [name]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        surname: undefined,
      };
    });
  }, [surname]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        title: undefined,
      };
    });
  }, [title]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        gsm: undefined,
      };
    });
  }, [gsm]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        password: undefined,
      };
    });
  }, [password]);

  useEffect(() => {
    if (authState.userId > 0) {
      setCondition(authState.userId === authState.company.managerId);
    }
  }, [authState]);

  console.log(errors);

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
              {/* {errors.title && <span className=""> {errors.title} </span> } */}
              <input
                placeholder="Name"
                className="company_input"
                onChange={(event) => setName(event.target.value)}
                value={name}
              />
              {/* {errors.name && <span className=""> {errors.name} </span> } */}

              <input
                placeholder="Surname"
                className="company_input"
                onChange={(event) => setSurname(event.target.value)}
                value={surname}
              />
              {/* {errors.surname && <span className=""> {errors.surname} </span> } */}
              <input
                placeholder="Gsm"
                className="company_input"
                onChange={(event) => setGsm(event.target.value)}
                value={gsm}
              />
              {/* {errors.gsm && <span className=""> {errors.gsm} </span> } */}
              <input
                placeholder="Email"
                className="company_input"
                onChange={(event) => setEmail(event.target.value)}
                value={email}
              />
              {/* {errors.email && <span className=""> {errors.email} </span> } */}

              <input
                type="password"
                placeholder="Password"
                className="company_input"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
              {/* {errors.password && <span className=""> {errors.password} </span> } */}

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
