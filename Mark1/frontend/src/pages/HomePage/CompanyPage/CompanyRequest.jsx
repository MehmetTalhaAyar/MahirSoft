import React, { useState } from "react";
// import { joinCompany } from "./api"; // Make sure this API call exists
import "./CompanyRequest.css"; // Ensure you have imported the CSS file
import { IoIosArrowForward } from "react-icons/io";
import { companyRequest } from "./api";

export default function JoinCompanyRequest() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [errors, setErrors] = useState({});

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    try {
      const response = await companyRequest({ name, description });
      if (response.status === 200) {
        // burada notification at

        setName("");
        setDescription("");
      }
    } catch (axiosError) {
      setErrors(axiosError.response.data.validationErrors);
    } 
    
  };

  return (
    <main className="main_request">
      <form onSubmit={handleSubmit} className="join_company_form">
        <h2 className="request_form_title">Request to Company</h2>
        <p className="requestcomany_form_paragraph">
        You can submit a request to create a company by entering the desired company name and description.
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
