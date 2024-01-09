import { createBrowserRouter } from "react-router-dom";
import { ForgotPassword } from "../pages/ForgotPasswordPage/ForgotPassword.jsx";
import MyProfile from "../pages/HomePage/ProfilePage/myprofile.jsx";
import Settings from "../pages/HomePage/ProfilePage/settings.jsx";
import Dashboard from "../pages/HomePage/TasksPage/dashboard.jsx";
import Board from "../pages/HomePage/DashboardPage/board.jsx";
import Project from "../pages/HomePage/Project/projects.jsx";
import App from "../App.jsx";
import Home from "../pages/HomePage/home.jsx";

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
          index: true,
          element: <Dashboard />,
        },
        {
          path: "dashboard",
          element: <Board />,
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
          path:"projects",
          element: <Project />
        }
      ],
    },
    {
      path: "/forgotpassword",
      element: <ForgotPassword />,
    },
  
  ]);