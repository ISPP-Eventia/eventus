import React from "react";
import { useNavigate } from "react-router";
import { useQuery } from "react-query";

import { EventUs } from "types";
import { eventApi } from "api";

import { Loader } from "components/atoms";
import { EventCard } from "components/molecules";


const MyEvents = () => {
    const navigate = useNavigate();
  
    const { isLoading, data: events } = useQuery("events", () =>
      eventApi.getEvents().then((response) => response.data as EventUs[])
    );
  
    return (
      <>
        {isLoading ? (
          <Loader />
        ) : (
          <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
            {events?.map((e) => (
              <EventCard event={e} />
            ))}
          </section>
        )}
      </>
    );
  };
  
  export default MyEvents;