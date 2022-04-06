import { useQuery } from "react-query";
import { Typography } from "@mui/material";

import { EventUs } from "types";
import { userApi } from "api";

import { Loader } from "components/atoms";
import { EventCard } from "components/molecules";
import { Link } from "react-router-dom";

const MyEvents = () => {
  const { isLoading, data: events } = useQuery("events", () =>
    userApi
      .getEventsByOrganizer()
      .then((response) => response?.data as EventUs[])
  );

  return (
    <section>
      <Typography variant="h4">Mis Eventos</Typography>
      {isLoading ? (
        <Loader />
      ) : events?.length === 0 ? (
        <div>
          No tiene ningún evento, <Link to="/events/new">crea uno!</Link>
        </div>
      ) : (
        <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {events?.map((event) => (
            <EventCard event={event} />
          ))}
        </section>
      )}
    </section>
  );
};

export default MyEvents;
