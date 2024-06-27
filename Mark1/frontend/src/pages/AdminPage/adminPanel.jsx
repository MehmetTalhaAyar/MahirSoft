import AdminSidebar from "./AdminSidebar";
import "./AdminPanel.css";
import AdminDashboard from "./AdminDashboard";
import { useAuthState } from "../../state/context";
import { useNavigate } from "react-router-dom";

const AdminPanel = () => {
  const authState = useAuthState();
  const navigate = useNavigate();
  if(authState.userId !== 1){
    navigate("/");
  }else{
    return (
      <div className="adminpanel">
        <AdminSidebar />
        <AdminDashboard />
      </div>
    );
  }

  
};

export default AdminPanel;
