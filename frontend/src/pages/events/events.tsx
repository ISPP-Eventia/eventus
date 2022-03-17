import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router";

import { EventCard } from "components/templates";
import Page from "../page";

import { EventUs } from "components/templates/events/eventCard";

const DummyEvent1: EventUs = {
  id: 1,
  image:
    "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
  title: "Pachanguita",
  description: "Unas partiditas",
  price: 5,
  date: "2019-01-16",
};

const DummyEvent2: EventUs = {
  id: 2,
  image: "https://emerac.files.wordpress.com/2011/03/etsii.jpg?w=584",
  title: "Hacer trabajo ISPP",
  description: "Presentacion",
  price: 5,
  date: "2021-01-18",
};

const EventListPage = () => {
  const navigate = useNavigate();

  const [events, setEvents] = React.useState<EventUs[]>();

  React.useEffect(() => {
    let isCancelled = false;

    // TODO: call api and get events

    !isCancelled &&
      setEvents([
        DummyEvent1,
        DummyEvent1,
        DummyEvent2,
        DummyEvent2,
        DummyEvent1,
        DummyEvent1,
        DummyEvent2,
        DummyEvent1,
        DummyEvent1,
        DummyEvent1,
      ]);

    return () => {
      isCancelled = true;
    };
  }, []);

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
