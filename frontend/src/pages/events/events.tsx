import React, { useEffect } from "react";
import { Button, Card, Typography } from "@mui/material";

import Page from "../page";
import { EventCard } from  "components/templates";
import { Description } from "@mui/icons-material";

export type EventUs = {
  image?: string;
  title?: string;
  description?: string;
  price?: number;
  date?: string;
}


const LandingSection = () => {
  const onContactClick = () => {
    window.scrollTo({
      behavior: "smooth",
      top: document.body.scrollHeight,
    });
  };

  const onNewEventClick = () => {
    console.log("Ventana para nuevo evento");
    /*history push*/
  };

  const PlaceHolderEvent: EventUs = {
      image: "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
      title: "Pachanguita",
      description: "Unas partiditas ",
      price: 5,
      date: "2019-01-16"
  };

  return (
    <div className="PEPE">
      <section className="relative mb-6 grid animate-fade-in">
      <div className="flex flex-col gap-5 xl:pl-24">
        <div className="h-11/12 flex flex-row gap-5">
          <Typography variant="h3">Eventos</Typography>
          <Typography variant="body1" className="mb-8">
            A continuación se muestran los
            eventos disponibles
          </Typography>
          <Button variant="contained" color="primary" onClick={onNewEventClick}>
            {" "}
            + New Event
          </Button>
        </div>
      </div>
      </section>
      <section className="relative mb-6 h-100 grid animate-fade-in min-h-fit max-w-fit">
      <div className="min-h-full flex flex-row flex-wrap gap-5">
        <EventCard event={PlaceHolderEvent}/>
        <EventCard event={PlaceHolderEvent}/>
        <EventCard event={PlaceHolderEvent}/>
        <EventCard event={PlaceHolderEvent}/>
        <EventCard event={PlaceHolderEvent}/>
      </div>
      </section>
      
      
      {/* <img className="h-11/12" src={LandingImg} alt="" /> */}
    </div>
  );
};

const EventList = () => {
  const [showCards, setShowCards] = React.useState(false);

  return (
    <Page title="">
      <div className="overflow-hidden">
        <LandingSection />
        {/* <section className="grid grid-cols-1 gap-4 md:grid-cols-2 md:gap-8 xl:grid-cols-4 xl:gap-12">
            {showCards
                ? cards.map((card) => <LandingCard {...card} key={card.title} />)
                : null} 
        </section>*/}
      </div>
    </Page>
  );
};

export default EventList;
