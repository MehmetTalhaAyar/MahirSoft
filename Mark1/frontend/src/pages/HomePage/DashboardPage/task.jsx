import React, { useEffect, useState } from "react";
import { FaCheck } from "react-icons/fa";
import "./task.css";

function Task(props) {
  // Access props data
  const { isNew,changeState } = props;
  const [taskName,setTaskName] = useState();
  const [taskDescription,setTaskDescription] = useState();

  const saveElements = () => {
    changeState()
  }
  
  


  if (isNew === true){
    return (
      <>
        <div className="task">
          <button className="save-button" onClick={saveElements}><FaCheck /></button>
          <div className="input-container">
          <input required name="task-name-field" maxLength="32" onChange={(event)=>setTaskName(event.target.value)} id="task-name-field" type="text" className="name-field task-input" />
          <label className="task-input-label" htmlFor="task-name-field">Enter a task Name</label>
          </div>
          <div className="input-container">
          <textarea required onChange={(event)=>{
            console.log(event)
            setTaskDescription(event.target.value)}} id="task-desçcription-field" name="task-description-field" type="text" className="description-field task-input" />
          <label className="task-input-label" htmlFor="task-description-field">Enter a task descrition</label>
          </div>
          <div className="tag">Tag</div>
        </div>
      </>
    )

  }
  else{
    return (
      <>
        <div className="task">
          <div className="task-name">
            <h1 className="task-readOnly-input name-field" name="task-header" id="task-header" readOnly={true}>{taskName}</h1>
            </div>
          <div className="task-description">
            <textarea name="task-body" id="task-body" className="task-readOnly-input description-field" readOnly={true}>{taskDescription}</textarea>
            </div>
          <div className="tag">Tag</div>
        </div>
      </>
    );

  }

  
}
export default Task;
