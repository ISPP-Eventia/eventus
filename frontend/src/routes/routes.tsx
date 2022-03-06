import React, { Suspense } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import { Loader } from "components/atoms";
import { LandingPage } from "pages";

const AppRoutes = () => {
  return (
    <Suspense
      fallback={
        <div className="h-full w-full flex items-center justify-center">
          <Loader />
        </div>
      }
    >
      <Router>
        <Routes>
          <Route path="/landing" element={<LandingPage />} />
          <Route path="*" element={<Navigate to="/landing" />} />
        </Routes>
      </Router>
    </Suspense>
  );
};

export default AppRoutes;
