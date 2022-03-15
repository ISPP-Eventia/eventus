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
        <div className="flex h-full w-full items-center justify-center">
          <Loader />
        </div>
      }
    >
      <main className="my-[60px] min-h-[calc(100vh-120px)]">
        <Router>
          <Routes>
            <Route path="/landing" element={<LandingPage />} />
            <Route path="*" element={<Navigate to="/landing" />} />
          </Routes>
        </Router>
      </main>
    </Suspense>
  );
};

export default AppRoutes;
