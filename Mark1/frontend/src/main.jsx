import React, { StrictMode } from "react";
import ReactDOM, { createRoot } from "react-dom/client";
import App from "./App.jsx";

import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./pages/HomePage/home.jsx";
import { ForgotPassword } from "./pages/ForgotPasswordPage/ForgotPassword.jsx";
import MyProfile from "./pages/HomePage/ProfilePage/myprofile.jsx";
import Settings from "./pages/HomePage/ProfilePage/settings.jsx";
import Dashboard from "./pages/HomePage/TasksPage/dashboard.jsx";
import Board from "./pages/HomePage/DashboardPage/board.jsx";

const router = createBrowserRouter([
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
    ],
  },
  {
    path: "/forgotpassword",
    element: <ForgotPassword />,
  },
  {
    path: "/settings",
    element: <Settings />,
  },
]);

const rootElement = document.getElementById("root");
const root = createRoot(rootElement);

root.render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);

// ReactDOM.createRoot(document.getElementById("root")).render(
//   <React.StrictMode>
//     <App />
//   </React.StrictMode>
// );
