// EditProfilePage.js
import React, { useState, useRef, useEffect } from "react";
import "./editprofile.css";
import { LuUpload } from "react-icons/lu";
import { MdDeleteOutline } from "react-icons/md";
import defaultProfileImage from "/src/assets/profileImage.jpg";
import { EditProfileImage } from "./api";
import { useAuthDispatch, useAuthState } from "../../../state/context";
import { toast } from "react-hot-toast";


function EditProfilePage({ onProfileImageChange }) {
  const authState = useAuthState();
  const [profileImage, setProfileImage] = useState(defaultProfileImage); // Upload the image on the editpage
  const [tempImage,setTempImage] = useState(null);
  const fileInputRef = useRef(null);
  const dispatch = useAuthDispatch();
  // Upload the image function
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    const fileReader = new FileReader();

        fileReader.onloadend = () => {
            const data = fileReader.result
            setTempImage(data);
        }
    fileReader.readAsDataURL(file)
    
  };

  useEffect(()=>{

    if(authState.image !== null && authState !== undefined){
      console.log(authState.image);

      setProfileImage(`/assets/profile/${authState.image}`);
    }

  },[])


  const handleUploadClick = () => {
    fileInputRef.current.click();
    console.log("Upload button clicked");
  };

  //Save the image on the Profile Card function
  const saveChanges = async () => {

    if(tempImage !== null){
      const response = await EditProfileImage({
        image:tempImage
      })

      if(response.status === 200){
        setProfileImage(`/assets/profile/${response.data.image}`);
        dispatch({type:'update-image',image:response.data.image});
        onProfileImageChange(response.data.image);
        toast.error("resim yüklendi.");
      }
    }
    
    setTempImage(null);
  };

  

  return (
    <>
    <div className="edit_profile_page">
      <div className="section2">
        <div className="profile_container2">Profile Image</div>
        <div className="_img">
          <img
            src={
              tempImage
                ? tempImage
                : profileImage
            }
            alt="Profile"
            className="_image3"
          />
          <div className="import_photo">
            <LuUpload className="upload" onClick={handleUploadClick} />
            <MdDeleteOutline
              className="delete"
              onClick={() => setTempImage(null)}
            />
            <input
              type="file"
              ref={fileInputRef}
              accept="image/*"
              onChange={handleImageChange}
              style={{ display: "none" }}
            />
          </div>
        </div>
      </div>
      
      <div className="profile_details2">
        <div className="details2">
          <div className="full_name">Full Name</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="company">Company</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="job">Job</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="about">About</div>
          <textarea
            type="text"
            className="textarea-control"
            id="textArea"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="country">Country</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="adress">Adress</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>

        <div className="details2">
          <div className="phone">Phone</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
        <div className="details2">
          <div className="email">Email</div>
          <input
            type="text"
            className="form-control"
            id="nametext"
            aria-describedby="name"
          />
        </div>
      </div>
      <div className="end_button">
        <button className="save_changes" onClick={saveChanges}>
          Save Changes
        </button>
      </div>
    </div>
    </>
  );
}

export default EditProfilePage;
