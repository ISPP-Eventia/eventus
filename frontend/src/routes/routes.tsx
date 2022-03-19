import React, { Suspense } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import { Loader } from "components/atoms";
import { EventListPage, EventDetailPage, NewEventPage } from "pages";
import { LandingPage, TestPage, ResultsPage} from "pages";

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
            <Route path="/results" element={<ResultsPage />} />
            <Route path="/test" element={<TestPage />} />
            <Route path="/events" element={<EventListPage />} />
            <Route path="/events/new" element={<NewEventPage />} />
            <Route path="/events/:id" element={<EventDetailPage />} />
            <Route path="*" element={<Navigate to="/landing" />} />
          </Routes>
        </Router>
      </main>
    </Suspense>
  );
};

export default AppRoutes;
