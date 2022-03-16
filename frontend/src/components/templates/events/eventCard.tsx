import React from "react";
import { Box, Card, Typography } from "@mui/material";

type EventUs = {
    image?: string;
    title?: string;
    description?: string;
    price?: number;
    date?: string;
}

const EventCard = (props: {event: EventUs} ) => {
  return (
    <Card className="max-w-sm h-80 w-60 hover:shadow-xl flex flex-col mx-4 my-4 -4min-w-max">
      <img className= "max-h-max object-contain h-45 w-60" src={props.event.image}/>
      <div className="flex flex-col justify-evenly ">
      <section className="w-full py-4 px-4 h-1/4">
        {props.event.title && <Typography variant="h5">{props.event.title}</Typography>}
      </section>
        <div className="h-1/2 py-2 px-4">
          {props.event.description && <Typography variant="h6">{props.event.description}</Typography>}
        </div>
      <div className="flex flex-row justify-between py-4 px-4 h-1/4">
        <div className="w-1/4">
          {props.event.price && <Typography variant="h6">{props.event.price}€</Typography>}
        </div>
        <div className="">
          {props.event.date && <Typography variant="h6">{props.event.date}</Typography>}
        </div>
        </div>
      </div>
      
    </Card>
  );
};

export default EventCard;