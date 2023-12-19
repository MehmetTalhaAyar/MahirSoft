import NavBar from "./navbar";
import SideBar from "./sidebar";
import "./home.css";
import Dashboard from "./DashboardPage/dashboard";

function Home() {
  return (
    <div>
      <NavBar />
      <div className="home_page_wraper">
        <SideBar />
        <Dashboard />
      </div>
    </div>
  );
}
export default Home;
