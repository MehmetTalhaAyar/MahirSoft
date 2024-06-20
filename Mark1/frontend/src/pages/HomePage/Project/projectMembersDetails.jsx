import React, { useEffect, useState } from "react";
import "./projectMembersDetails.css";
import { FaUsers } from "react-icons/fa";
import { MdDelete } from "react-icons/md";
import { IoIosWarning } from "react-icons/io";
import AsyncSelect from "react-select/async";
import WarningModal from "./WarningModal";

export default function ProjectMembersDetails({ members, setIsModalOpen }) {
  const [memberNames, setMemberNames] = useState([]);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [deleteIndex, setDeleteIndex] = useState(null);

  useEffect(() => {
    if (members.length > 0) {
      setMemberNames(members);
    }
  }, [members]);

  const handleDeleteMembers = (index) => {
    setDeleteIndex(index);
    setShowConfirmModal(true);
  };

  const handleConfirmDelete = () => {
    setMemberNames(memberNames.filter((_, i) => i !== deleteIndex));
    setShowConfirmModal(false);
  };

  const handleCancelDelete = () => {
    setShowConfirmModal(false);
  };

  return (
    <section className="project_member">
      <div className="project_member_container">
        <div className="project_member_icon">
          <FaUsers className="member_icon" />
          <h1 className="project_member_title">Project Members</h1>
        </div>
        <div className="yetki_and_members">
          <div className="add_members_container">
            <AsyncSelect
              className="add_members "
              placeholder="Members"
              cacheOptions
              isMulti
            />
            <button>Add Members</button>
          </div>
          <span className="yetki_button" onClick={() => setIsModalOpen(true)}>
            Yetki Verme
          </span>
        </div>
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
                <td>{item.gsm}</td>
                <td>{item.company.name}</td>
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
      {showConfirmModal && (
        <WarningModal
          icon={<IoIosWarning />}
          title="Are you sure?"
          paragraph="You won't be able to revert this"
          delete="Delete"
          cancel="Cancel"
          onConfirm={handleConfirmDelete}
          onCancel={handleCancelDelete}
          isEditMode={false}
        />
      )}
    </section>
  );
}
