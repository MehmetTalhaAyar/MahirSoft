import { useEffect, useState } from "react";
import { signIn } from "./api";
import "./Form.css";
import { SignFormItem } from "../../components/SignFormItem";
import { useAuthDispatch } from "../../state/context";
import { useNavigate } from "react-router-dom";

function Form({ isVisible, changeVisible,changeForm, isChangeActive}) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [isEmailTrue,setIsEmailTrue] = useState(false);
  const [isPasswordTrue,setIsPasswordTrue] = useState(false);
  const [isRememberActive,setIsRememberActive] = useState(false);
  const navigate = useNavigate();
  const dispatch = useAuthDispatch();


  const onsubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await signIn({
        email,
        password,
      });
      
      console.log("istek tamamlandı.", response);
      if (response.data !== "") {
        if(isRememberActive){
          dispatch({type:'remember-login-success',data:response.data})
        }else{
  
          dispatch({type:'login-success',data:response.data})
        }
        if(response.data.user.userId === 1){
          navigate("/admin")
        }else{

          navigate("/home")
        }

      } else if (response.status === 400) {
        console.log("bad request.");
      } else if (response.status === 204) {
        setIsEmailTrue(true);
        setIsPasswordTrue(true);
        console.log("No context");
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
    <div className={`form-container ${isVisible ? "visible animate " : ""}${isChangeActive ? "left-animation" : ""}`}>
      <form className="Sign-In-Form" onSubmit={onsubmit}>
        <div className="form-header">
          <span className="change-form" title="Go to Sign Up Form" onClick={changeForm}>
            &larr;
          </span>
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

          <SignFormItem inputId="login-email" error={isEmailTrue} errorMessage="Please be sure that your email is correct" onChange={(event) =>setEmail(event.target.value)} name="E-mail" errorType="not-found-error"/>
          <SignFormItem type="password" inputId="login-password" error={isPasswordTrue} errorMessage="Please be sure that your password is correct" onChange={(event) =>setPassword(event.target.value)} name="Password" errorType="not-found-error"/>

          <div className="remember-forgot">
            <label>
              <input type="checkbox" onClick={()=>setIsRememberActive(!isRememberActive)} />
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
