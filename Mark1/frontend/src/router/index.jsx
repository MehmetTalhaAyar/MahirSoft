import { createBrowserRouter } from "react-router-dom";
import { ForgotPassword } from "../pages/ForgotPasswordPage/ForgotPassword.jsx";
import MyProfile from "../pages/HomePage/ProfilePage/myprofile.jsx";
import Settings from "../pages/HomePage/ProfilePage/settings.jsx";
import Dashboard from "../pages/HomePage/TasksPage/dashboard.jsx";
import Board from "../pages/HomePage/DashboardPage/board.jsx";
import Project from "../pages/HomePage/Project/projects.jsx";
import App from "../App.jsx";
import Home from "../pages/HomePage/home.jsx";
import { CompanyPage } from "../pages/HomePage/CompanyPage/company.jsx";
import { TaskPage } from "../pages/TaskPage/index.jsx";
import ProjectDetails from "../pages/HomePage/Project/projectDetails.jsx";
import AdminPanel from "../pages/AdminPage/AdminPanel.jsx";
import AdminDashboard from "../pages/AdminPage/AdminDashboard.jsx";
import Users from "../pages/AdminPage/users.jsx";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/home",
    element: <Home />,
    children: [
      {
        path: "",
        element: <Board />,
        index: true,
      },

      {
        path: "myprofile",
        element: <MyProfile />,
      },
      {
        path: "settings",
        element: <Settings />,
      },
      {
        path: "projects",
        element: <Project />,
      },
      {
        path: "projectdetails",
        element: <ProjectDetails />,
      },
      {
        path: "projects/:projectname",
        element: <Dashboard />,
      },
      {
        path: "task/:taskid",
        element: <TaskPage />,
      },
      {
        path: "company",
        element: <CompanyPage />,
      },
    ],
  },
  {
    path: "/forgotpassword",
    element: <ForgotPassword />,
  },
  {
    path: "/admin",
    element: <AdminPanel />,
    children: [
      { path: "adminDashboard", element: <AdminDashboard /> },
      { path: "users", element: <Users /> },
    ],
  },
]);
