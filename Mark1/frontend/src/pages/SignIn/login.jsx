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
      />
      <SignUpForm
        isVisible={isSignUpFormVisible}
        changeVisible={toggleSignUpFormVisibility}
      />
    </div>
  );
}
export default Login;
