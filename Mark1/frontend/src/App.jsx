import { useNavigate } from "react-router-dom";
import Login from "./pages/SignIn/login";
import { useAuthState } from "./state/context";

function App() {
  const navigate = useNavigate();
  const authState = useAuthState();

  if(authState.userId > 0){
    navigate("/home")
  }

  return (
    <>
    
      <Login />
    
    </>
  );
}
export default App;
