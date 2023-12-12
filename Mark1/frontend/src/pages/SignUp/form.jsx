import { Link, useNavigate } from "react-router-dom";

function Form({toHome}) {
  return (
    <div className="form-container">
      <form>
        <h1>Login</h1>
        <div className="input-box">
          <span className="icon"></span>
          <input id="email" />
          <label htmlFor="email">E-mail</label>
        </div>
        <div className="input-box">
          <span className="icon"></span>
          <input id="password" type="password" />
          <label htmlFor="password">Password</label>
        </div>

        <div className="remember-forgot">
          <label>
            <input type="checkbox" />
            Remember Me
          </label>
          <a href="#" className="fg">
            Forgot Password
          </a>
        </div>
        <button type="submit" class="button-link" onClick={toHome}>
            Log-in
        </button>
      </form>
    </div>
  );
}
export default Form;
