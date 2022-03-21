import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router";

import { EventUs } from "types";

import { EventCard } from "components/molecules";
import Page from "../page";
import { eventApi } from "api";

const EventListPage = () => {
  const navigate = useNavigate();

  const [events, setEvents] = React.useState<EventUs[]>();
  const [refetch, setRefetch] = React.useState(false);


  React.useEffect(() => {
    eventApi
      .getEvents()
      .then((response) => {
        setEvents(response.data);
        console.log(response.data)
      })
      .catch((error) => console.log("error", "Users not fetched: " + error.message));
  }, [refetch]);

  const onNewEventClick = () => {
    navigate("/events/new");
  };

  const AddEvent = (
    <Button variant="contained" color="primary" onClick={onNewEventClick}>
      New Event
    </Button>
  );

  return (
    <Page title="Eventos disponibles" actions={AddEvent}>
      <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
        {events?.map((e) => (
          <EventCard event={e} />
        ))}
      </section>
    </Page>
  );
};

export default EventListPage;