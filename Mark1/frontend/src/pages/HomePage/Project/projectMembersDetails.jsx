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
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },

    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },

    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },

    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "5363935530",
      company: "ABC Inc",
    },
  ]);

  const handleDeleteMembers = (index) => {
    setMemberNames(memberNames.filter((_, i) => i != index));
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
              <th>Role</th>
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