import Dashboard from "./DashboardPage/dashboard";
import NavBar from "./navbar";
import SideBar from "./sidebar";
import "./home.css";

function Home() {
  return (
    <div>
      <NavBar />
      <div className="container">
        <SideBar />
        <Dashboard />
      </div>
    </div>
  );
}
export default Home;
