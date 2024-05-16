import React, { useState } from "react";

import "./projectDetailsDescription.css";
export default function ProjectDetailsDescription() {
  const [description, setDescription] = useState(false);
  return (
    <div className="description_details">
      <h3>Description</h3>
      <p>
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Praesentium
        odio at provident, distinctio quos iure inventore obcaecati atque, odit
        ea facere cumque. Sit hic cumque doloremque, nihil quod adipisci
      </p>
    </div>
  );
}
