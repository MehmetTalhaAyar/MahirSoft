import { useState } from "react";
import { signIn } from "./api";

function Form({ toHome }) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();

  console.log(email);
  console.log(password);

  const onsubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await signIn({
        email,
        password,
      });
      console.log("istek tamamlandı.", response.data);
      toHome();
    } catch (ex) {
      console.error("istek başarısız.", ex);
    }
  };

  return (
    <div className="form-container">
      <form onSubmit={onsubmit}>
        <h1>Login</h1>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="email"
            onChange={(event) => setEmail(event.target.value)}
          />
          <label htmlFor="email">E-mail</label>
        </div>
        <div className="input-box">
          <span className="icon"></span>
          <input
            id="password"
            type="password"
            onChange={(event) => setPassword(event.target.value)}
          />
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
        <button type="submit" className="button">
          Log-in
        </button>
      </form>
    </div>
  );
}

export default Form;
