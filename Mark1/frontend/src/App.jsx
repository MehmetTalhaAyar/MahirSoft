import Form from "./pages/SignUp/form";
import NavBar from "./pages/SignUp/navbar";
import "./pages/SignUp/navbar.css";
import "./pages/SignUp/form.css";
import { useState } from "react";
function App() {
  const [isFormVisible, setIsFormVisible] = useState(false);

  const toggleFormVisibility = () => {
    setIsFormVisible(!isFormVisible);
  };

  return (
    <>
      <NavBar onLoginClick={toggleFormVisibility} />

      {isFormVisible && <Form />}
    </>
  );
}
export default App;
