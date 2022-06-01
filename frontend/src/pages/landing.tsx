import { useEffect, useState } from "react";
import { Button, Card, Typography } from "@mui/material";
import { useNavigate } from "react-router";

import Page from "./page";

import LandingImg from "assets/landing.png";
import HostImg from "assets/host.svg";
import AttendImg from "assets/attend.svg";
import SponsorImg from "assets/sponsor.svg";
import OrganizeImg from "assets/organize.svg";

const LandingSection = () => {
  const navigate = useNavigate();
  const onStartClick = () => {
    navigate("/events");
  };

  return (
    <section className="relative mb-6 grid animate-fade-in grid-cols-1 items-center md:grid-cols-2">
      <div className="h-11/12 flex flex-col gap-5 xl:pl-24">
        <Typography variant="h3">Eventos que marcarán tu vida</Typography>
        <Typography variant="body1" className="mb-8">
          Eventus es una app web para la organización, participación, promoción
          y alojamiento de eventos tanto personales como empresariales
        </Typography>
        <Button variant="outlined" color="primary" onClick={onStartClick}>
          ¡Comencemos!
        </Button>
      </div>
      <img className="h-11/12" src={LandingImg} alt="" />
    </section>
  );
};

const LandingCard = (props: { img: string; title: string; text: string }) => {
  return (
    <Card className="m-2 flex animate-fade-in-top flex-col gap-5 p-2">
      <img src={props.img} alt="img" className="h-20 animate-fade-in p-2" />
      <div className="px-2 pb-4">
        <Typography variant="h6">{props.title}</Typography>
        <Typography variant="body2">{props.text}</Typography>
      </div>
    </Card>
  );
};

const LandingPage = () => {
  const [showCards, setShowCards] = useState(false);

  const cards = [
    {
      title: "Organiza",
      img: OrganizeImg,
      text: "Crea y gestiona eventos, compártelos, consigue patrocinadores, encuentra el mejor alojamiento...",
    },
    {
      title: "Participa",
      img: AttendImg,
      text: "Forma parte de los mejores eventos, participa en eventos competitivos y gana dinero...",
    },
    {
      title: "Patrocina",
      img: SponsorImg,
      text: "Patrocina eventos increibles, proporciona anuncios y date a conocer...",
    },
    {
      title: "Aloja",
      img: HostImg,
      text: "Llena de vida tu infraestructura ofreciéndola como alojamiento para los eventos...",
    },
  ];

  const videos = [
    <iframe
      className="mx-auto h-48 w-full rounded-xl"
      src="https://www.youtube.com/embed/mMcjV0LqYxE"
      title="EventUs Demo"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
      allowFullScreen
    ></iframe>,
    <iframe
      className="mx-auto h-48 w-full rounded-xl"
      src="https://www.youtube.com/embed/EBOd49JMabo"
      title="EventUs Anuncio Clientes"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
      allowFullScreen
    ></iframe>,
    <iframe
      className="mx-auto h-48 w-full rounded-xl"
      src="https://www.youtube.com/embed/aXTTmlg4NxI"
      title="EventUs Anuncio Inversores"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
      allowFullScreen
    ></iframe>,
  ];

  useEffect(() => {
    let cancelled = false;

    let timeout = setTimeout(() => {
      if (!cancelled) {
        setShowCards(true);
      }
    }, 1000);

    return () => {
      cancelled = true;
      clearTimeout(timeout);
    };
  }, []);

  return (
    <Page title="">
      <div className="overflow-hidden">
        <LandingSection />

        <section className="grid grid-cols-1 gap-4 md:grid-cols-2 md:gap-8 xl:grid-cols-4 xl:gap-12">
          {showCards
            ? cards.map((card) => <LandingCard {...card} key={card.title} />)
            : null}
        </section>

        <section className="mt-20 grid grid-cols-1 items-center gap-4 md:grid-cols-3 md:gap-8 xl:gap-12">
          {showCards && videos}
        </section>
      </div>
    </Page>
  );
};

export default LandingPage;
