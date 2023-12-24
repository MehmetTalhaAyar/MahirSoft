import Form from "./form";
import NavBar from "./navbar";
import SignUpForm from "./SignUpForm";
import "./Login.css";

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [isButtonClicked, setIsButtonClicked] = useState(false);
  const [isSignUpFormVisible, setIsSignUpFormVisible] = useState(false);
  const [isSignUpButtonClicked, setIsSignUpButtonClicked] = useState(false);
  const [isChangeSignUpFormActive,setIsChangeSignUpFormActive] = useState(undefined);
  const [isChangeSignInFormActive,setIsChangeSignInFormActive] = useState(undefined);

  const toggleFormVisibility = () => {
    if (isSignUpFormVisible === true) setIsSignUpFormVisible(false);
    setIsFormVisible(!isFormVisible);
    setIsButtonClicked(true);
  };

  const toggleSignUpFormVisibility = () => {
    if (isFormVisible === true) setIsFormVisible(false);
    setIsSignUpFormVisible(!isSignUpFormVisible);
    setIsSignUpButtonClicked(true);
  };

  const changeSignUpForm = () =>{
    setIsChangeSignUpFormActive(true);
  
  }

  const changeSignInForm = () => {
    setIsChangeSignInFormActive(true);
    
  }


  useEffect(
    ()=>{
      if(isChangeSignUpFormActive){
        const timeout = setTimeout(()=>{
          setIsSignUpFormVisible(false);
          setIsFormVisible(true);
          setIsChangeSignUpFormActive(undefined);
        },500)
      }
    }
  ,[isChangeSignUpFormActive])

  useEffect(
    ()=>{
      if(isChangeSignInFormActive){
        const timeout = setTimeout(()=>{
          setIsFormVisible(false);
          setIsSignUpFormVisible(true);
          setIsChangeSignInFormActive(undefined);
        },500)
      }
    }
  ,[isChangeSignInFormActive])

  useEffect(() => {
    if (isButtonClicked) {
      // Set a timeout to remove the "clicked" class after 40 milliseconds (adjust as needed)
      const timeoutId = setTimeout(() => {
        setIsButtonClicked(false);
      }, 40);

      // Clear the timeout when the component is unmounted or when isButtonClicked changes
      return () => clearTimeout(timeoutId);
    }
  }, [isButtonClicked]);

  useEffect(() => {
    if (isSignUpButtonClicked) {
      // Set a timeout to remove the "clicked" class after 40 milliseconds (adjust as needed)
      const timeoutId = setTimeout(() => {
        setIsSignUpButtonClicked(false);
      }, 40);

      // Clear the timeout when the component is unmounted or when isButtonClicked changes
      return () => clearTimeout(timeoutId);
    }
  }, [isSignUpButtonClicked]);

  const navigateHome = useNavigate();

  const navigateToHome = () => {
    navigateHome("/home");
  };

  return (
    <div>
      <NavBar
        onLoginClick={toggleFormVisibility}
        isLoginButtonClicked={isButtonClicked}
        onSignUpClick={toggleSignUpFormVisibility}
        isSignUpButtonClicked={isSignUpButtonClicked}
      />

      <Form
        toHome={navigateToHome}
        isVisible={isFormVisible}
        changeVisible={toggleFormVisibility}
        changeForm = {changeSignInForm}
        isChangeActive = {isChangeSignInFormActive}
      />
      <SignUpForm
        isVisible={isSignUpFormVisible}
        changeVisible={toggleSignUpFormVisibility}
        changeForm ={changeSignUpForm}
        isChangeActive = {isChangeSignUpFormActive}
      />
    </div>
  );
}
export default Login;
