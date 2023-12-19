import Form from "./pages/SignIn/form";
import NavBar from "./pages/SignIn/navbar";
import "./pages/SignIn/navbar.css";
import "./pages/SignIn/form.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import SignUpForm from "./pages/SignIn/SignUpForm";

function App() {
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [isButtonClicked, setIsButtonClicked] = useState(false);
  const [isSignUpFormVisible, setIsSignUpFormVisible] = useState(false);
  const [isSignUpButtonClicked, setIsSignUpButtonClicked] = useState(false);
  const navigateHome = useNavigate();

  const toggleFormVisibility = () => {
    if (isSignUpFormVisible === true)
      setIsSignUpFormVisible(false)
    setIsFormVisible(!isFormVisible);
    setIsButtonClicked(true);
  };

  const toggleSignUpFormVisibility = () => {
    if (isFormVisible === true)
      setIsFormVisible(false)
    setIsSignUpFormVisible(!isSignUpFormVisible);
    setIsSignUpButtonClicked(true);
  };
  const navigateToHome = () => {
    navigateHome("/home");
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

  

  return (
    <>
      <NavBar
        onLoginClick={toggleFormVisibility}
        isLoginButtonClicked={isButtonClicked}
        onSignUpClick = {toggleSignUpFormVisibility}
        isSignUpButtonClicked = {isSignUpButtonClicked}

      />

      <Form toHome={navigateToHome} isVisible={isFormVisible} changeVisible={toggleFormVisibility}/>
      <SignUpForm isVisible={isSignUpFormVisible} changeVisible={toggleSignUpFormVisibility}/>
    </>
  );
}
export default App;
