import Form from "./pages/SignUp/form";
import NavBar from "./pages/SignUp/navbar";
import "./pages/SignUp/navbar.css";
import "./pages/SignUp/form.css";
import { useState, useEffect } from "react";
import {useNavigate} from "react-router-dom";

function App() {
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [isButtonClicked, setIsButtonClicked] = useState(false);
  const navigate = useNavigate();

  const toggleFormVisibility = () => {
    setIsFormVisible(!isFormVisible);
    setIsButtonClicked(true);
  };

  const navigateToHome = () => {
    navigate('/home')
  }

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

  return (
    <>
      <NavBar
        onLoginClick={toggleFormVisibility}
        isButtonClicked={isButtonClicked}
      />

      {isFormVisible && <Form toHome = {navigateToHome}/>}
    </>
  );
}
export default App;
