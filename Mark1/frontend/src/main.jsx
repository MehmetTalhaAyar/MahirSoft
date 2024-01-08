import React, { StrictMode } from "react";
import { createRoot } from "react-dom/client";


import {  RouterProvider } from "react-router-dom";

import { AuthenticationContext } from "./state/context.jsx";
import { router } from "./router";


const rootElement = document.getElementById("root");
const root = createRoot(rootElement);

root.render(
  <StrictMode>
    <AuthenticationContext>
      <RouterProvider router={router} />
    </AuthenticationContext>
  </StrictMode>
);

// ReactDOM.createRoot(document.getElementById("root")).render(
//   <React.StrictMode>
//     <App />
//   </React.StrictMode>
// );
