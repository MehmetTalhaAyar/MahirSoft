import "./index.css";
import { HiDotsHorizontal } from "react-icons/hi";
import { CgProfile } from "react-icons/cg";

import React, { useCallback, useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { useAuthState } from "../../state/context";
import { getTaskInfo } from "./api";
import { MONTHS } from "../../Constants/Constants";

export function TaskPage() {
  const [dropDownMenu, setDropDownMenu] = useState(false);
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const location = useLocation();
  const authState = useAuthState();

  const [taskName,setTaskName] = useState(location.state.name)
  const [taskDescription,setTaskDescription] = useState(location.state.description)
  const [stage,setStage] = useState();
  const [taskResponsible,setTaskResponsible] = useState({});
  const [taskReporter,setTaskReporter] = useState({});
  const [createdDate,setCreatedDate] = useState({});
  const [userDefaultLogo,setUserDefaultLogo] = useState();

  
  useEffect(()=>{
    if(authState.userId > 0){
        setUserDefaultLogo(authState.name[0]+authState.surname[0])
    }
  },[authState])

  useEffect(()=>{
    getInfo()
  },[])

  const getInfo = useCallback(async()=>{

    const response = await getTaskInfo(location.state.id);
    
    if(response.status == 200){
        setStage(response.data.stage.name)
        setTaskReporter({logo:response.data.createdById.name[0]+response.data.createdById.surname[0] ,name:response.data.createdById.fullName})
        setTaskResponsible({logo:response.data.responsibleId.name[0]+response.data.responsibleId.surname[0] ,name:response.data.responsibleId.fullName})
        setCreatedDate({
            day: response.data.createdOn.split('T')[0].split('-')[2],
            month: response.data.createdOn.split('T')[0].split('-')[1],
            year :response.data.createdOn.split('T')[0].split('-')[0]
          })
        setComments(response.data.comments)
        

    }
    else{
        console.log("Some Thing Went Wrong!")
    }

  },[])

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
          <h1 className="task_title">{taskName}</h1>
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
            {taskDescription}
            </p>

            <h4>Activity</h4>
            <div className="comment_container">
              <p>Show :</p>
              <div className="comments_title">Comments</div>
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
            <h5 className="task_name">{stage}</h5>
            <h4 className="assignee_title">Assignee</h4>
            <div className="name_container">
              <p className="logo2">{taskResponsible.logo}</p>
              <h3>{taskResponsible.name}</h3>
            </div>
            <h4 className="reporter_title">Reporter</h4>
            <div className="name_container">
              <p className="logo3">{taskReporter.logo}</p>
              <h3>{taskReporter.name}</h3>
            </div>
            <hr className="tree" />
            <p className="task_created">Created on {`${MONTHS[createdDate.month]} ${createdDate.day},${createdDate.year}`}</p>
            {/* <p className="task_updated">Updated February 3,2024, 15:51 PM</p> */}
          </div>
        </div>
      </div>
    </main>
  );
}
