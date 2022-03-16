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


  const PlaceHolderEvent: EventUs = {
      image: "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
      title: "Pachanguita",
      description: "Unas partiditas ",
      price: 5,
      date: "2019-01-16"
  };

  const PlaceHolderEvent2: EventUs = {
    image: "https://upload.wikimedia.org/wikipedia/commons/5/5b/ETSI_Inform%C3%A1tica_Sevilla_y_DrupalCamp_Spain_2011.jpg",
    title: "Hacer trabajo ISPP",
    description: "Presentacion ",
    price: 5,
    date: "2019-01-16"
};



  return (
    
      <section className="mt-6 gap-5 grid m-auto grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5 animate-fade-in max-w-fit">
          <EventCard event={PlaceHolderEvent}/>
          <EventCard event={PlaceHolderEvent2}/>
          <EventCard event={PlaceHolderEvent}/>
          <EventCard event={PlaceHolderEvent2}/>
          <EventCard event={PlaceHolderEvent}/>
      </section>
  );
};

const EventList = () => {
  const [showCards, setShowCards] = React.useState(false);

  const onNewEventClick = () => {
    console.log("Ventana para nuevo evento");
    /*history push*/
  }; 

  const AddEvent = 
    <Button variant="contained" color="primary" onClick={onNewEventClick}>
    {" "}
    + New Event
    </Button>
  

  return (
    <Page title="Eventos disponibles" actions={AddEvent}>
      <div className="">
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
