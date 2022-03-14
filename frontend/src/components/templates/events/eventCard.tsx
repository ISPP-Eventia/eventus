import React from "react";
import { Box, Card, Typography } from "@mui/material";

type EventUs = {
    image?: string;
    title?: string;
    description?: string;
}

const EventCard = (props: {event: EventUs} ) => {
  return (
    <Card className="max-w-xs">
    <img className= "max-h-max" src={props.event.image}/>
    <section className="block w-full py-5 px-4 md:px-8 lg:px-24 xl:px-48">
      {props.event.title && <Typography variant="h4">{props.event.title}</Typography>}
    </section>
    
    </Card>
  );
};

export default EventCard;