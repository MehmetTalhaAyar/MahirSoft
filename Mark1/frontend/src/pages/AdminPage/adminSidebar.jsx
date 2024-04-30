import "./AdminSidebar.css";
import { Link } from "react-router-dom";

const AdminSidebar = () => {
  return (
    <div className="admin_sidebar">
      <ul className="sidebar_elements">
        <Link to={"adminDashboard"}>
          <li>Dashboard</li>
        </Link>
        <Link to={"users"}>
          <li>Users</li>
        </Link>
        <li>Logout</li>
      </ul>
    </div>
  );
};

export default AdminSidebar;
