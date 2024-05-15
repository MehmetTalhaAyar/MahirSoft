import "./index.css";

import { CgProfile } from "react-icons/cg";
import AsyncSelect from "react-select/async";
import Select from "react-select";
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";

import React, { useCallback, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useAuthState } from "../../state/context";
import {
  getTaskInfo,
  addComment,
  getprojectMembers,
  updateTaskInfo,
  updateComment,
  updateLikeCount,
  deleteComment,
} from "./api";
import { MONTHS } from "../../Constants/Constants";
import Description from "./description";
import Dropdown from "./dropdown";
import {
  AiOutlineAlignLeft,
  AiOutlineLike,
  AiOutlineMenu,
} from "react-icons/ai";
import { BsReply } from "react-icons/bs";
import { MdDelete, MdOutlineModeEditOutline } from "react-icons/md";

export function TaskPage() {
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const [editedComment, setEditedComment] = useState("");
  const [editingCommentIndex, setEditingCommentIndex] = useState(null);
  const location = useLocation();
  const authState = useAuthState();

  const [taskName, setTaskName] = useState(location.state.name);
  const [stageId, setStageId] = useState(location.state.id);
  const [taskDescription, setTaskDescription] = useState(
    location.state.description
  );
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
  const [isShow, setIsShow] = useState(false);
  const [saveChanges, setSaveChanges] = useState(false);

  useEffect(() => {
    if (authState.userId > 0) {
      setUserDefaultLogo(authState.name[0] + authState.surname[0]);
    }
  }, [authState]);

  useEffect(() => {
    const response = getInfo();

    response.finally(() => {
      setIsShow(true);
    });
  }, []);

  const getInfo = useCallback(async () => {
    const response = await getTaskInfo(location.state.id);

    if (response.status == 200) {
      setStage(response.data.stage.name);
      setTaskReporter(response.data.reportsTo);

      setTaskDescription(response.data.taskDescripton);

      setReporterLogo(
        response.data.reportsTo.name[0] + response.data.reportsTo.surname[0]
      );

      setTaskResponsible(response.data.responsibleId);
      setResponsibleLogo(
        response.data.responsibleId.name[0] +
          response.data.responsibleId.surname[0]
      );
      setCreatedDate({
        day: response.data.createdOn[2],
        month: `${response.data.createdOn[1]}`,
        year: response.data.createdOn[0],
      });
      setComments(
        response.data.comments.map((comment) => {
          return {
            commentId: comment.commentId,
            authorId: comment.writtenById.userId,
            author: comment.writtenById.fullName,
            authorImg: comment.writtenById.image,
            time: `${comment.createdOn[2]} ${MONTHS[comment.createdOn[1]]} ${
              comment.createdOn[0]
            }`,
            text: comment.content,
            likeCount: comment.likeCount,
          };
        })
      );
      if (response.data.taskDeadlineDate !== null) {
        setSelectedDate(new Date(response.data.taskDeadlineDate));
      }
      await getProjectMembersAndStage({
        stageId: response.data.stage.id,
        searchKey: "",
      });
    } else {
      console.log("Some Thing Went Wrong!");
    }
  }, []);

  const getProjectMembersAndStage = useCallback(async (body) => {
    const response = await getprojectMembers(body);

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
        commentId: response.data.commentId,
        author: response.data.writtenById.fullName, // Replace with actual user information
        authorId: response.data.writtenById.userId,
        authorImg: response.data.writtenById.image,
        time: `${response.data.createdOn[2]} ${
          MONTHS[response.data.createdOn[1]]
        } ${response.data.createdOn[0]}`,
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
      setTaskResponsible(response.data.responsibleId);
      setTaskReporter(response.data.reporterUser);
      setReporterLogo(
        response.data.reporterUser.name[0] +
          response.data.reporterUser.surname[0]
      );
      setResponsibleLogo(
        response.data.responsibleId.name[0] +
          response.data.responsibleId.surname[0]
      );
    }
    setSaveChanges(true);
    return setTimeout(() => {
      setSaveChanges(false);
    }, 1500);
  };

  const filterMembers = (inputValue) => {
    return inputValue.users.map((user) => {
      return {
        value: user.userId,
        label: user.fullName,
      };
    });
  };

  const promiseOptions = async (inputValue) => {
    const response = await getprojectMembers({
      stageId: stageId,
      searchKey: inputValue,
    });
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(filterMembers(response.data));
      }, 100);
    });
  };

  const handleLikeChange = async (index) => {
    const updatedComments = [...comments];

    const response = await updateLikeCount(updatedComments[index].commentId);

    if (response.status === 200) {
      updatedComments[index].likeCount = response.data.likeCount;
    }

    setComments(updatedComments);
  };

  const handleDeleteComment = async (index) => {
    const updatedComments = [...comments];
    const response = await deleteComment(updatedComments[index].commentId);
    if (response.status === 200) {
      updatedComments.splice(index, 1);
      setComments(updatedComments);
    }
  };

  const handleEditClick = (index, text) => {
    setEditingCommentIndex(index);
    setEditedComment(text);
  };

  const handleSaveEdit = async (index) => {
    const updatedComments = [...comments];
    const response = await updateComment({
      commentId: updatedComments[index].commentId,
      content: editedComment,
      taskId: location.state.id,
    });

    if (response.status === 200) {
      updatedComments[index].text = editedComment;
      setComments(updatedComments);
    }

    setEditingCommentIndex(null);
  };

  const handleCancelEdit = () => {
    setEditedComment("");
    setEditingCommentIndex(null);
  };

  return isShow ? (
    <main>
      <div className="task_page_container">
        <div className="task_page_container_header">
          <h1 className="task_title">{taskName}</h1>
          <Dropdown />
        </div>
        <div className="alt_container">
          <div className="left_container">
            <Description description={taskDescription} />
            <div className="activity">
              <AiOutlineAlignLeft />
              <h4>Activity</h4>
            </div>

            <div className="photo_input_container">
              {
                authState.image !== null ? 
                <img className="profile-image" src={`/assets/profile/${authState.image}`} alt="profile image" /> :
                <p className="logo">{userDefaultLogo}</p>

              }
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
<<<<<<< HEAD
                  <p className="logo2">{userDefaultLogo}</p>
=======
                  <h4>
                    <img className="profile-image-comment" src={`/assets/profile/${comment.authorImg}`} alt="profile image" />
                  </h4>
>>>>>>> f16bd2276620bcaca204e736e76add5a5d207509
                  <div className="show_comment_right">
                    <div className="author_title">
                      <section className="send_time">
                        <h4>{comment.author}</h4>
                        <p className="time">{comment.time}</p>
                      </section>
                      <div className="clickable_icon">
                        <div className="like_container">
                          <span className="like_number">
                            {comment.likeCount || 0}
                          </span>
                          <section className="like_img">
                            <AiOutlineLike
                              onClick={() => handleLikeChange(index)}
                            />
                          </section>
                        </div>

                        {comment.authorId === authState.userId ? (
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
                        ) : (
                          <></>
                        )}
                        {comment.authorId === authState.userId ? (
                          <section className="delete_img">
                            <MdDelete
                              onClick={() => handleDeleteComment(index)}
                            />
                          </section>
                        ) : (
                          <></>
                        )}
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
              {taskResponsible.image !== null ?  
              <img className="profile-image-report" src={`/assets/profile/${taskResponsible.image}`} alt="profile image" /> :
              <p className="logo2">{responsibleLogo}</p>
              }
              <AsyncSelect
                defaultOptions={projectMembers}
                cacheOptions
                loadOptions={promiseOptions}
                placeholder={taskResponsible.fullName}
                className="assignee_select_box"
                onChange={handleResponsibleSelector}
              />
            </div>
            <h4 className="reporter_title">Reporter</h4>
            <div className="name_container">
              {taskReporter.image !== null ?
              <img className="profile-image-report" src={`/assets/profile/${taskReporter.image}`} alt="profile image" /> :
              <p className="logo3">{reporterLogo}</p>
              }
              <AsyncSelect
                defaultOptions={projectMembers}
                cacheOptions
                loadOptions={promiseOptions}
                placeholder={taskReporter.fullName}
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

            {saveChanges ? (
              <span className="change_successfully">
                Successfully saved changes{" "}
              </span>
            ) : (
              ""
            )}

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
  ) : (
    <></>
  );
}
