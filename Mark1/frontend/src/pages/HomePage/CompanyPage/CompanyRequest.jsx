import React, { useState } from "react";
// import { joinCompany } from "./api"; // Make sure this API call exists
import "./CompanyRequest.css"; // Ensure you have imported the CSS file
import { IoIosArrowForward } from "react-icons/io";

export default function JoinCompanyRequest({ onRequestSuccess }) {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [errors, setErrors] = useState({});

  const handleSubmit = async (event) => {
    {
      /*event.preventDefault();
    try {
      const response = await joinCompany({ name, email });
      if (response.status === 201) {
        onRequestSuccess();
      }
    } catch (axiosError) {
      setErrors(axiosError.response.data.validationErrors);
    } */
    }
  };

  return (
    <main className="main_request">
      <form onSubmit={handleSubmit} className="join_company_form">
        <h2 className="request_form_title">Request to Company</h2>
        <p className="requestcomany_form_paragraph">
          Please enter the name of the company you want to join and provide a
          brief description of why you want to join this company.
        </p>
        <input
          placeholder="Name"
          className="form_input_name"
          onChange={(event) => setName(event.target.value)}
          value={name}
        />
        <textarea
          placeholder="Description"
          className="form_textarea_name"
          onChange={(event) => setDescription(event.target.value)}
          value={description}
        />
        <div className="button_request_container">
          <button className="request_company_button" type="submit">
            Request
            <IoIosArrowForward className="arrow-icon" />
          </button>
        </div>
      </form>
    </main>
  );
}
