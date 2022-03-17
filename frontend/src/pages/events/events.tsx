import React from "react";
import { Button } from "@mui/material";

import Page from "../page";
import { EventCard } from "components/templates";
import { useNavigate } from "react-router";

export type EventUs = {
  image?: string;
  title?: string;
  description?: string;
  price?: number;
  date?: string;
};

const PlaceHolderEvent: EventUs = {
  image:
    "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
  title: "Pachanguita",
  description: "Unas partiditas",
  price: 5,
  date: "2019-01-16",
};

const PlaceHolderEvent2: EventUs = {
  image:
    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fes.wikipedia.org%2Fwiki%2FEscuela_T%25C3%25A9cnica_Superior_de_Ingenier%25C3%25ADa_Inform%25C3%25A1tica_(Universidad_de_Sevilla)&psig=AOvVaw2FlqhEuRXlIyqszEZeLD_o&ust=1647614533224000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCJiG49awzfYCFQAAAAAdAAAAABAD",
  title: "Hacer trabajo ISPP",
  description: "Presentacion",
  price: 5,
  date: "2021-01-18",
};

const EventListPage = () => {
  const navigate = useNavigate();

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
        <EventCard event={PlaceHolderEvent} />
        <EventCard event={PlaceHolderEvent2} />
        <EventCard event={PlaceHolderEvent} />
        <EventCard event={PlaceHolderEvent2} />
        <EventCard event={PlaceHolderEvent} />
      </section>
    </Page>
  );
};

export default EventListPage;
