import React, { StrictMode } from "react";
import { createRoot } from "react-dom/client";

import { RouterProvider } from "react-router-dom";

import { AuthenticationContext } from "./state/context.jsx";
import { router } from "./router";
import { Toaster } from "react-hot-toast";

const rootElement = document.getElementById("root");
const root = createRoot(rootElement);

root.render(
  <StrictMode>
    <AuthenticationContext>
      <Toaster position="top-right" />

      <RouterProvider router={router} />
    </AuthenticationContext>
  </StrictMode>
);

// ReactDOM.createRoot(document.getElementById("root")).render(
//   <React.StrictMode>
//     <App />
//   </React.StrictMode>
// );
