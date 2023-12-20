import React, { StrictMode } from "react";
import ReactDOM, { createRoot } from "react-dom/client";
import App from "./App.jsx";

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import Home from "./pages/HomePage/home.jsx";
import { ForgotPassword } from "./pages/ForgotPasswordPage/ForgotPassword.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
  },
  {
    path: "/home",
    element: <Home/>
  },
  {
    path: "/forgotpassword",
    element: <ForgotPassword/>,
  },
]);


const rootElement = document.getElementById('root')
const root = createRoot(rootElement)

root.render(
  <StrictMode>
     <RouterProvider router={router} />
  </StrictMode>
)


// ReactDOM.createRoot(document.getElementById("root")).render(
//   <React.StrictMode>
//     <App />
//   </React.StrictMode>
// );
