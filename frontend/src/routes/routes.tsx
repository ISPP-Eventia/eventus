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
  SessionPage,
  ProfilePage,
  ErrorPage,
} from "pages";
import EditEventPage from "pages/events/editEvent";
import EditLocationPage from "pages/locations/editLocation";
import EditProfilePage from "pages/profile/editProfile";

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

          <Route path="/events" element={<EventListPage />} />
          <Route path="/events/new" element={<NewEventPage />} />
          <Route path="/events" element={<EventListPage />} />
          <Route path="/events/new" element={<NewEventPage />} />
          <Route path="/events/:id" element={<EventDetailPage />} />
          <Route path="/events/:id/edit" element={<EditEventPage />} />

          <Route path="/locations" element={<LocationListPage />} />
          <Route path="/locations/new" element={<NewLocationPage />} />
          <Route path="/locations/:id" element={<LocationDetailPage />} />
          <Route path="/locations/:id/edit" element={<EditLocationPage />} />

          <Route path="/profile/:tab" element={<ProfilePage />} />
          <Route path="/profile/:id/edit" element={<EditProfilePage/>} />
          <Route path="/profile" element={<Navigate to="/profile/events" />} />

          <Route
            path="/404"
            element={<ErrorPage errorMessage="404!, not found!" />}
          />
          <Route path="*" element={<Navigate to="/landing" />} />
        </Routes>
      </main>
    </Suspense>
  );
};

export default AppRoutes;
