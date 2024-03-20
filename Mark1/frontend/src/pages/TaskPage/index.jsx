import "./index.css";

import { CgProfile } from "react-icons/cg";
import { AiOutlineLike } from "react-icons/ai";
import { BsReply } from "react-icons/bs";
import { MdDelete } from "react-icons/md";
import { MdOutlineModeEditOutline } from "react-icons/md";
import { AiOutlineAlignLeft } from "react-icons/ai";
import { AiOutlineMenu } from "react-icons/ai";

import Select from "react-select";
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";

import React, { useCallback, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useAuthState } from "../../state/context";
import {
  getTaskInfo,
  deleteTaskById,
  addComment,
  getprojectMembers,
  updateTaskInfo,
} from "./api";
import { MONTHS } from "../../Constants/Constants";
import Description from "./description";
import Dropdown from "./dropdown";

export function TaskPage() {
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const [editedComment, setEditedComment] = useState("");
  const [editingCommentIndex, setEditingCommentIndex] = useState(null);

  const location = useLocation();
  const authState = useAuthState();

  const [taskName, setTaskName] = useState(location.state.name);

  const [stage, setStage] = useState();
  const [taskResponsible, setTaskResponsible] = useState();
  const [taskReporter, setTaskReporter] = useState();
  const [createdDate, setCreatedDate] = useState({});
  const [userDefaultLogo, setUserDefaultLogo] = useState();
  const [responsibleLogo, setResponsibleLogo] = useState();
  const [reporterLogo, setReporterLogo] = useState();
  const [selectedDate, setSelectedDate] = useState();
  const [projectMembers, setProjectMembers] = useState({});
  const [stageOptions, setStageOptions] = useState({});
  const [selectedStage, setSelectedStage] = useState();
  const [selectedResponsible, setSelectedResponsible] = useState();
  const [selectedReporter, setSelectedReporter] = useState();

  useEffect(() => {
    if (authState.userId > 0) {
      setUserDefaultLogo(authState.name[0] + authState.surname[0]);
    }
  }, [authState]);

  useEffect(() => {
    getInfo();
  }, []);

  const getInfo = useCallback(async () => {
    const response = await getTaskInfo(location.state.id);

    if (response.status == 200) {
      setStage(response.data.stage.name);
      setTaskReporter(response.data.reportsTo.fullName);
      setReporterLogo(
        response.data.reportsTo.name[0] + response.data.reportsTo.surname[0]
      );

      setTaskResponsible(response.data.responsibleId.fullName);
      setResponsibleLogo(
        response.data.responsibleId.name[0] +
          response.data.responsibleId.surname[0]
      );
      setCreatedDate({
        day: response.data.createdOn.split("T")[0].split("-")[2],
        month: response.data.createdOn.split("T")[0].split("-")[1],
        year: response.data.createdOn.split("T")[0].split("-")[0],
      });
      setComments(
        response.data.comments.map((comment) => {
          return {
            author: comment.writtenById.fullName,
            time: `${comment.createdOn.split("T")[0].split("-")[2]} ${
              MONTHS[comment.createdOn.split("T")[0].split("-")[1]]
            } ${comment.createdOn.split("T")[0].split("-")[0]}`,
            text: comment.content,
          };
        })
      );
      if (response.data.taskDeadlineDate !== null) {
        setSelectedDate(new Date(response.data.taskDeadlineDate));
      }
      await getProjectMembersAndStage(response.data.stage.id);
    } else {
      console.log("Some Thing Went Wrong!");
    }
  }, []);

  const getProjectMembersAndStage = useCallback(async (stageId) => {
    const response = await getprojectMembers(stageId);

    if (response.status === 200) {
      setProjectMembers(
        response.data.users.map((user) => {
          return {
            value: user.id,
            label: user.fullName,
          };
        })
      );

      setStageOptions(
        response.data.stages.map((stage) => {
          return {
            value: stage.id,
            label: stage.name,
          };
        })
      );
    }
  }, []);

  const handleStageSelector = (selectedValue) => {
    setSelectedStage(selectedValue);
  };

  const handleResponsibleSelector = (selectedValue) => {
    setSelectedResponsible(selectedValue);
  };

  const handleReporterSelector = (selectedValue) => {
    setSelectedReporter(selectedValue);
  };

  const handleCommentSubmit = async () => {
    if (commentText.trim() === "") {
      return;
    }

    const response = await addComment({
      content: commentText,
      linkedTaskId: location.state.id,
    });

    if (response.status === 201) {
      const newComment = {
        author: response.data.writtenById.fullName, // Replace with actual user information
        time: `${response.data.createdOn.split("T")[0].split("-")[2]} ${
          MONTHS[response.data.createdOn.split("T")[0].split("-")[1]]
        } ${response.data.createdOn.split("T")[0].split("-")[0]}`,
        text: commentText,
      };

      setComments([...comments, newComment]);
      setCommentText("");
    } else {
      console.log("Something went wrong!");
    }
  };

  const handleChanges = async () => {
    if (
      selectedDate === undefined &&
      selectedReporter === undefined &&
      selectedResponsible === undefined &&
      selectedStage === undefined
    )
      return;

    const response = await updateTaskInfo(
      {
        responsibleId:
          selectedResponsible !== undefined ? selectedResponsible.value : null,
        stageId: selectedStage !== undefined ? selectedStage.value : null,
        endDate: selectedDate,
        reportsToId:
          selectedReporter !== undefined ? selectedReporter.value : null,
      },
      location.state.id
    );

    if (response.status === 200) {
      setReporterLogo(
        response.data.reporterUser.name[0] +
          response.data.reporterUser.surname[0]
      );
      setResponsibleLogo(
        response.data.responsibleId.name[0] +
          response.data.responsibleId.surname[0]
      );
    }
  };

  const handleLikeChange = (index) => {
    const updatedComments = [...comments];
    updatedComments[index].likesCount =
      (updatedComments[index].likesCount || 0) + 1;
    setComments(updatedComments);
  };

  const handleDeleteComment = (index) => {
    const updatedComments = [...comments];
    updatedComments.splice(index, 1);
    setComments(updatedComments);
  };

  const handleEditClick = (index, text) => {
    setEditingCommentIndex(index);
    setEditedComment(text);
  };

  const handleSaveEdit = (index) => {
    const updatedComments = [...comments];
    updatedComments[index].text = editedComment; // Update the text of the comment
    setComments(updatedComments);
    setEditingCommentIndex(null); // Reset editing state
  };

  const handleCancelEdit = () => {
    setEditedComment(""); // Clear the edited comment
    setEditingCommentIndex(null); // Reset editing state
  };
  return (
    <main>
      <div className="task_page_container">
        <div className="task_page_container_header">
          <h1 className="task_title">{taskName}</h1>
          <Dropdown />
        </div>
        <div className="alt_container">
          <div className="left_container">
            <Description />
            <div className="activity">
              <AiOutlineAlignLeft />
              <h4>Activity</h4>
            </div>

            <div className="photo_input_container">
              <p className="logo">{userDefaultLogo}</p>
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
            <div className="comment_container">
              <AiOutlineMenu />
              <div className="comments_title">Comments</div>
            </div>
            <div className="show_comment_container">
              {comments.map((comment, index) => (
                <div key={index} className="show_comment">
                  <h4>
                    <CgProfile className="photo" />
                  </h4>
                  <div className="show_comment_right">
                    <div className="author_title">
                      <section className="send_time">
                        <h4>{comment.author}</h4>
                        <p className="time">{comment.time}</p>
                      </section>
                      <div className="clickable_icon">
                        <div className="like_container">
                          <span className="like_number">
                            {comment.likesCount || 0}
                          </span>
                          <section className="like_img">
                            <AiOutlineLike
                              onClick={() => handleLikeChange(index)}
                            />
                          </section>
                        </div>
                        <section className="replay_img">
                          <BsReply />
                        </section>
                        <section className="edit_img">
                          {editingCommentIndex === index ? (
                            <MdOutlineModeEditOutline />
                          ) : (
                            <MdOutlineModeEditOutline
                              onClick={() =>
                                handleEditClick(index, comment.text)
                              }
                            />
                          )}
                        </section>
                        <section className="delete_img">
                          <MdDelete
                            onClick={() => handleDeleteComment(index)}
                          />
                        </section>
                      </div>
                    </div>
                    {editingCommentIndex === index ? (
                      <div>
                        <textarea
                          className="edit_comment_area"
                          value={editedComment}
                          onChange={(e) => setEditedComment(e.target.value)}
                        />
                        <span className="save_cancel">
                          <button
                            className="save"
                            onClick={() => handleSaveEdit(index)}
                          >
                            Save
                          </button>
                          <span className="cancel" onClick={handleCancelEdit}>
                            Cancel
                          </span>
                        </span>
                      </div>
                    ) : (
                      <p className="show">{comment.text}</p>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>
          <div className="right_container">
            <div className="selection_menu">
              <Select
                selected={selectedStage}
                options={stageOptions}
                placeholder={stage}
                className="task_name"
                onChange={handleStageSelector}
              />
              <div className="date_picker_container">
                <DatePicker
                  selected={selectedDate}
                  onChange={(date) => setSelectedDate(date)}
                  showTimeSelect
                  placeholderText="No Deadline"
                  timeFormat="HH:mm"
                  timeIntervals={15}
                  dateFormat="MMMM d, yyyy h:mm aa"
                  className="date_picker"
                />
              </div>
            </div>
            <h4 className="assignee_title">Assignee</h4>
            <div className="name_container">
              <p className="logo2">{responsibleLogo}</p>
              <Select
                options={projectMembers}
                placeholder={taskResponsible}
                className="assignee_select_box"
                onChange={handleResponsibleSelector}
              />
            </div>
            <h4 className="reporter_title">Reporter</h4>
            <div className="name_container">
              <p className="logo3">{reporterLogo}</p>
              <Select
                options={projectMembers}
                placeholder={taskReporter}
                className="assignee_select_box"
                onChange={handleReporterSelector}
              />
            </div>
            <div className="task_button_container">
              <button
                className="save_task_button"
                type="submit"
                onClick={handleChanges}
              >
                Save Changes
              </button>
            </div>
            <hr className="tree" />
            <p className="task_created">
              Created on{" "}
              {`${MONTHS[createdDate.month]} ${createdDate.day} ,${
                createdDate.year
              }`}
            </p>
            {/* <p className="task_updated">Updated February 3,2024, 15:51 PM</p> */}
          </div>
        </div>
      </div>
    </main>
  );
}
