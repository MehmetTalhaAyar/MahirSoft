import "./index.css";
import { HiDotsHorizontal } from "react-icons/hi";
import { CgProfile } from "react-icons/cg";

import React, { useState } from "react";

export function TaskPage() {
  const [dropDownMenu, setDropDownMenu] = useState(false);
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");

  const openDropdownMenu2 = () => {
    setDropDownMenu(!dropDownMenu);
  };

  const handleCommentSubmit = () => {
    if (commentText.trim() !== "") {
      const newComment = {
        author: "Redon Capuni", // Replace with actual user information
        time: new Date().toLocaleString(),
        text: commentText,
      };

      setComments([...comments, newComment]);
      setCommentText("");
    }
  };

  return (
    <main>
      <div className="task_page_container">
        <div className="task_page_container_header">
          <h1 className="task_title">Task Name</h1>
          <div>
            <HiDotsHorizontal
              className="_dot"
              onClick={() => openDropdownMenu2()}
            />

            <div
              className={`dropdown-content2 ${
                dropDownMenu ? "openDropdown2" : " "
              }`}
            >
              <a href="#delete">Delete</a>
              <a href="#edit">Edit</a>
            </div>
          </div>
        </div>
        <div className="alt_container">
          <div className="left_container">
            <h4 className="task_description_header">Description</h4>
            <p className="task_description">
              As a user I would to be able to access all categories from a
              multi-level navigation on the home page, so that I can get around
              the site a lot faster.
            </p>

            <h4>Activity</h4>
            <div className="comment_container">
              <p>Show :</p>
              <div className="comments_title">Comments</div>
            </div>
            <div className="photo_input_container">
              <p className="logo">RC</p>
              <textarea
                type="text"
                className="comment_input"
                placeholder="Add a comment..."
                id="textArea"
                aria-describedby="name"
                value={commentText}
                onChange={(e) => setCommentText(e.target.value)}
              />
            </div>
            <div className="button_container">
              <button className="comment_button" onClick={handleCommentSubmit}>
                Comment
              </button>
            </div>

            <div className="show_comment_container">
              {comments.map((comment, index) => (
                <div key={index} className="show_comment">
                  <h4>
                    <CgProfile className="photo" />
                  </h4>
                  <div className="show_comment_right">
                    <div className="send_time">
                      <h4>{comment.author}</h4>
                      <p className="time">{comment.time}</p>
                    </div>
                    <p className="show">{comment.text}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
          <div className="right_container">
            <h5 className="task_name">To Do</h5>
            <h4 className="assignee_title">Assignee</h4>
            <div className="name_container">
              <p className="logo2">RC</p>
              <h3>Redon Capuni</h3>
            </div>
            <h4 className="reporter_title">Reporter</h4>
            <div className="name_container">
              <p className="logo3">RC</p>
              <h3>Redon Capuni</h3>
            </div>
            <hr className="tree" />
            <p className="task_created">Created May 3,2024, 7:51 PM</p>
            <p className="task_updated">Updated February 3,2024, 15:51 PM</p>
          </div>
        </div>
      </div>
    </main>
  );
}
