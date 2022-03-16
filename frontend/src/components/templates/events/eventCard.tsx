import React from "react";
import { Box, Card, Typography } from "@mui/material";

type EventUs = {
  image?: string;
  title?: string;
  description?: string;
  price?: number;
  date?: string;
};

const EventCard = (props: { event: EventUs }) => {
  return (
    <Card className="flex flex-col hover:shadow-xl">
      <header className="w-full">
        <img
          alt="img"
          className="h-28 w-full object-cover"
          src={props.event.image}
        />
      </header>
      <section className="mt-2 flex h-44 flex-col px-2">
        {props.event.title && (
          <Typography variant="h5">{props.event.title}</Typography>
        )}
        {props.event.description && (
          <Typography variant="body1">{props.event.description}</Typography>
        )}
        <div className="mt-auto flex flex-row items-center justify-between">
          <div className="w-1/4">
            {props.event.price && (
              <Typography variant="h6">{props.event.price}€</Typography>
            )}
          </div>
          <div className="">
            {props.event.date && (
              <Typography variant="body2">{props.event.date}</Typography>
            )}
          </div>
        </div>
      </section>
    </Card>
  );
};

export default EventCard;
