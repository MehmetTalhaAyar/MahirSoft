import "./SignUpForm.css"


function SignUpForm({isVisible,changeVisible}) {


  return (
    <>
      <div className={`SignUp-form-container ${isVisible ? "visible animate" : ""}`}>
      <form className="Sign-Up-Form">
      <div className="form-header">
        <span onClick={changeVisible} className="close" title="Close Sign In Form">&times;</span>
      </div>
      <div className="Sign-Up-Form-Content">
        <h1>Sign Up</h1>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="firstname"
            required
          />
          <label htmlFor="firstname">Name</label>
        </div>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="surname"
            required
          />
          <label htmlFor="surname">Surname</label>
        </div>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="GSM"
            required
          />
          <label htmlFor="GSM">GSM</label>
        </div>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="email"
            required
          />
          <label htmlFor="email">E-mail</label>
        </div>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="password"
            type="password"
            required
          />
          <label htmlFor="password">Password</label>
        </div>
        <button type="submit" className="button">
          Sign Up
        </button>
        </div>
      </form>
      </div>
    </>
  )
  }
  
  export default SignUpForm;
  