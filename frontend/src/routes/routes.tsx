import { Suspense } from "react";
import { Route, Routes, Navigate } from "react-router-dom";

import { Loader } from "components/atoms";
import {
  EventDetailPage,
  EventListPage,
  NewEventPage,
  LocationDetailPage,
  NewLocationPage,
  LocationListPage,
  LandingPage,
  TestPage,
  SessionPage,
  ProfilePage,
} from "pages";

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
        <Routes>
          <Route path="/landing" element={<LandingPage />} />
          <Route path="/login" element={<SessionPage />} />
          <Route path="/signup" element={<SessionPage />} />
          <Route path="/test" element={<TestPage />} />

          <Route path="/events" element={<EventListPage />} />
          <Route path="/events/new" element={<NewEventPage />} />
          <Route path="/events" element={<EventListPage />} />
          <Route path="/events/new" element={<NewEventPage />} />
          <Route path="/events/:id" element={<EventDetailPage />} />

          <Route path="/locations" element={<LocationListPage />} />
          <Route path="/locations/new" element={<NewLocationPage />} />
          <Route path="/locations/:id" element={<LocationDetailPage />} />

          <Route path="/profile/:tab" element={<ProfilePage />} />
          <Route path="/profile" element={<Navigate to="/profile/events" />} />

          <Route path="*" element={<Navigate to="/landing" />} />
        </Routes>
      </main>
    </Suspense>
  );
};

export default AppRoutes;
