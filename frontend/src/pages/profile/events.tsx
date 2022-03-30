import React from "react";
import { useNavigate } from "react-router";
import { useQuery } from "react-query";

import { EventUs } from "types";
import { eventApi } from "api";

import { Loader } from "components/atoms";
import { EventCard } from "components/molecules";
import { Typography } from "@mui/material";


const MyEvents = () => {
    const navigate = useNavigate();
  
    const { isLoading, data: events } = useQuery("events", () =>
      eventApi.getEventsByOrganizer().then((response) => response.data as EventUs[])
    );
  
    return (
      <section>
        <Typography variant="h4">Mis Eventos</Typography>
        {isLoading ? (
          <Loader />
        ) : (
          <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
            {events?.map((e) => (
              <EventCard event={e} />
            ))}
          </section>
        )}
      </section>
    );
  };
  
  export default MyEvents;