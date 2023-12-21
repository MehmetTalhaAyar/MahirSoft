import { useEffect, useState } from "react";
import { signIn } from "./api";
import "./Form.css";

function Form({ toHome, isVisible, changeVisible }) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [isEmailTrue,setIsEmailTrue] = useState(false);
  const [isPasswordTrue,setIsPasswordTrue] = useState(false);

  console.log(email);
  console.log(password);

  const onsubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await signIn({
        email,
        password,
      });
      console.log("istek tamamlandı.", response);
      if (response.data !== "") {
        toHome();
      } else if (response.status === 400) {
        console.log("bad request.");
      } else if (response.status === 204) {
        setIsEmailTrue(true);
        setIsPasswordTrue(true);
        console.log(":D");
      }
    } catch (ex) {

      console.error("istek başarısız.", ex);
    }
  };
  useEffect(()=>{
    setIsEmailTrue(false);
  },[email])

  useEffect(()=>{
    setIsPasswordTrue(false);
  },[password])


  return (
    <div className={`form-container ${isVisible ? "visible animate" : ""}`}>
      <form className="Sign-In-Form" onSubmit={onsubmit}>
        <div className="form-header">
          <span
            onClick={changeVisible}
            className="close"
            title="Close Log In Form"
          >
            &times;
          </span>
        </div>
        <div className="Sign-In-Form-Content">
          <h1>Login</h1>
          <div className="input-box">
            <span className="icon"></span>
            <input
              id="email"
              className={`${isEmailTrue ? "not-found-error" : ""}`}
              onChange={(event) => setEmail(event.target.value)}
              required
            />
            <label htmlFor="email">E-mail</label>
            {isEmailTrue && <span className="not-found-error">Please be sure that your email is true</span>}
          </div>
          <div className="input-box">
            <span className="icon"></span>
            <input
              id="password"
              type="password"
              className={`${isPasswordTrue ? "not-found-error" : ""}`}
              onChange={(event) => setPassword(event.target.value)}
              required
            />
            <label htmlFor="password">Password</label>
            {isPasswordTrue && <span className="not-found-error">Please be sure that your password is true</span>}
          </div>
          <div className="remember-forgot">
            <label>
              <input type="checkbox" />
              Remember Me
            </label>
            <a href="/forgotpassword" className="fg">
              Forgot Password
            </a>
          </div>
          <button type="submit" className="login_button">
            Log-in
          </button>
        </div>
      </form>
    </div>
  );
}

export default Form;
