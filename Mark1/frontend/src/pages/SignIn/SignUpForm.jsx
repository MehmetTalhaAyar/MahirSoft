import "./SignUpForm.css";
import { useEffect, useState } from "react";
import { signUp } from "./api";
import { SignFormItem } from "../../components/SignFormItem";

function SignUpForm({ isVisible, changeVisible,changeForm,isChangeActive}) {

  const [name,setName] = useState();
  const [surname,setSurname] = useState();
  const [gsm,setGsm] = useState();
  const [email,setEmail] = useState();
  const [password,setPassword] = useState();
  const [errors,setErrors] = useState({});
  const [successMessage,setSuccessMessage] = useState();
  const [leftAnimation,setLeftAnimation] = useState(false);  

  // bu loglar kaldırılacak
  console.log(name);
  console.log(surname);
  console.log(gsm);
  console.log(email);
  console.log(password);

  const onsubmit = async (event) => {
    event.preventDefault();


    try{
      const response = await signUp(
        {
          name,
          surname,
          gsm,
          email,
          password
        }
      );
      if (response.status === 200){

        //başarılı mesajı yazdır.
        console.log("başarılı istek.",response)
        setSuccessMessage(response.data)
        // changeVisible()
      }
    }
    catch(axiosError){
      if (axiosError.response.data.status === 400) {
        setErrors(axiosError.response.data.validationErrors)
      }
      console.log(errors)
      console.log(axiosError);
    }




  }
  useEffect(()=>{
    setErrors(function(lastErrors){
    return {
      ...lastErrors,
      name: undefined
    };
  })
  },[name])

  useEffect(()=>{
    setErrors(function(lastErrors){
    return {
      ...lastErrors,
      surname: undefined
    };
  })
  },[surname])


  useEffect(()=>{
    setErrors(function(lastErrors){
    return {
      ...lastErrors,
      gsm: undefined
    };
  })
  },[gsm])

  useEffect(()=>{
    setErrors(function(lastErrors){
    return {
      ...lastErrors,
      email: undefined
    };
  })
  },[email])

  useEffect(()=>{
    setErrors(function(lastErrors){
    return {
      ...lastErrors,
      password: undefined
    };
  })
  },[password])

  useEffect(()=>{
    if (successMessage){
      const timeout = setTimeout(()=>{
        setSuccessMessage(undefined);
      },1500);
      return () => clearTimeout(timeout);
    }
  },[successMessage])



  
  

  return (
    <>
      <div
        className={`SignUp-form-container ${
          isVisible ? "visible animate " : ""
        }${isChangeActive ? "left-animation" : ""}`}
      >
        <form className="Sign-Up-Form" onSubmit={onsubmit}>
          <div className="form-header">
            <span className="change-form" title="Go to Sign In Form" onClick={changeForm}>
            &larr;
            </span>
            <span
              onClick={changeVisible}
              className="close"
              title="Close Sign Up Form"
            >
              &times;
            </span>
          </div>
          <div className="Sign-Up-Form-Content">
            <h1>Sign Up</h1>

            <SignFormItem inputId="firstname" error={errors.name} errorMessage={errors.name} onChange={(event) =>setName(event.target.value)} name="Name" errorType="not-valid"/>
            <SignFormItem inputId="surname" error={errors.surname} errorMessage={errors.surname} onChange={(event) =>setSurname(event.target.value)} name="Surname" errorType="not-valid" />
            <SignFormItem inputId="GSM" error={errors.gsm} errorMessage={errors.gsm} onChange={(event) =>setGsm(event.target.value)} name="GSM" errorType="not-valid" />
            <SignFormItem inputId="email" error={errors.email} errorMessage={errors.email} onChange={(event) =>setEmail(event.target.value)} name="E-mail" errorType="not-valid" />
            <SignFormItem inputId="password" error={errors.password} errorMessage={errors.password} onChange={(event) =>setPassword(event.target.value)} name="Password" errorType="not-valid" />

            { successMessage && <div className="validation-success"> <span> {successMessage} </span></div> }
            <button type="submit" className="button">
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </>
  );
}

export default SignUpForm;
