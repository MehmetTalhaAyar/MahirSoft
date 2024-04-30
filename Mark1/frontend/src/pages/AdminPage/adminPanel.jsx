import AdminSidebar from "./AdminSidebar";
import "./AdminPanel.css";
import AdminDashboard from "./AdminDashboard";

const AdminPanel = () => {
  return (
    <div className="adminpanel">
      <AdminSidebar />
      <AdminDashboard />
    </div>
  );
};

export default AdminPanel;
