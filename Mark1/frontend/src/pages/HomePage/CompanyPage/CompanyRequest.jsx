import React, { useState } from "react";

export default function CompanyRequest() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission logic here
    console.log("Name:", name);
    console.log("Description:", description);
  };

  return (
    <div className="request_container">
      <section>
        <h1>Create a new Company </h1>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Praesentium
          odio at provident, distinctio quos iure inventore obcaecati atque,
          odit ea facere cumque. Sit hic cumque doloremque, nihil quod adipisci
          suscipit! Lorem ipsum dolor sit amet consectetur adipisicing elit.
          Illum vitae, dolorem eum suscipit neque, vel nihil sunt obcaecati
          saepe qui fugit repellat nesciunt minus aliquam. Error autem
          asperiores fugit facere!
        </p>
      </section>
      {/* <div className="form_request">
        <form onSubmit={handleSubmit} className="request-form">
          <div className="form-group">
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="description">Description:</label>
            <textarea
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="submit-button">
            Submit Request
          </button>
        </form>
      </div> */}
    </div>
  );
}
