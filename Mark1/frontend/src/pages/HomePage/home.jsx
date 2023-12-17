import NavBar from "./navbar";
import SideBar from "./sidebar";
import "./home.css";
import Dashboard from "./DashboardPage/dashboard";

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
