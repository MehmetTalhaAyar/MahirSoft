import React, { useState } from "react";
import "./projectMembersDetails.css";
import { FaUsers } from "react-icons/fa";
import { MdDelete } from "react-icons/md";

export default function ProjectMembersDetails({ setIsModalOpen }) {
  const [memberNames, setMemberNames] = useState([
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "MahirSoft",
    },
    {
      fullName: "Redon",
      email: "redon@gmail.com",
      GSB: "5363935530",
      company: "MahirSoft",
    },
    {
      fullName: "Sara",
      email: "sara@example.com",
      GSB: "531561144452",
      company: "MahirSoft",
    },
    {
      fullName: "Ahmed",
      email: "ahmed@example.com",
      GSB: "245245254",
      company: "MahirSoft",
    },
    {
      fullName: "Sabo",
      email: "sabo@example.com",
      GSB: "752752752",
      company: "MahirSoft",
    },
    {
      fullName: "Mehmet",
      email: "mehmet@example.com",
      GSB: "75275252",
      company: "MahirSoft",
    },

    {
      fullName: "Juli",
      email: "juli@example.com",
      GSB: "752752752",
      company: "MahirSoft",
    },
    {
      fullName: "Uvejs",
      email: "uvejs@example.com",
      GSB: "75275257",
      company: "MahirSoft",
    },

    {
      fullName: "Aqif",
      email: "aqif@example.com",
      GSB: "75272752",
      company: "MahirSoft",
    },
    {
      fullName: "Emir",
      email: "emir@example.com",
      GSB: "752752752",
      company: "MahirSoft",
    },

    {
      fullName: "Abdullah",
      email: "abdullah@example.com",
      GSB: "7252572456",
      company: "MahirSoft",
    },
    {
      fullName: "Nokee",
      email: "noke@example.com",
      GSB: "5434989",
      company: "MahirSoft",
    },
  ]);

  const handleDeleteMembers = (index) => {
    setMemberNames(memberNames.filter((_, i) => i !== index));
  };

  return (
    <section className="project_member">
      <div className="project_member_container">
        <div className="project_member_icon">
          <FaUsers className="member_icon" />
          <h1 className="project_member_title">Project Member</h1>
        </div>
        <span className="yetki_button" onClick={() => setIsModalOpen(true)}>
          Yetki Verme
        </span>
      </div>
      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>Full Name</th>
              <th>Email</th>
              <th>GSM</th>
              <th>Company</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {memberNames.map((item, index) => (
              <tr key={index}>
                <td>{item.fullName}</td>
                <td>{item.email}</td>
                <td>{item.GSB}</td>
                <td>{item.company}</td>
                <td>
                  <MdDelete
                    onClick={() => handleDeleteMembers(index)}
                    className="delete_member"
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
}
