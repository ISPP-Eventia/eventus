import React from "react";
import { useNavigate } from "react-router";
import { Card, Typography } from "@mui/material";

import { Infraestructure } from "types";

const InfraestructureCard = (props: { event: Infraestructure }) => {
  const navigate = useNavigate();

  const onClick = () => {
    navigate(`/infraestructures/${props.event.id}`);
  };

  return (
    <Card
      className="flex cursor-pointer flex-col hover:shadow-xl"
      onClick={onClick}
    >
      <header className="w-full">
        <img
          alt="img"
          className="h-28 w-full object-cover"
          src={props.event.image}
        />
      </header>
      <section className="mt-2 flex h-44 flex-col px-2">
        {props.event.location && (
          <Typography variant="h5">{props.event.location}</Typography>
        )}
      </section>
      <footer className="mt-auto flex flex-row items-center justify-between px-2">
        {props.event.price && (
          <Typography className="w-1/4" variant="h6">
            {props.event.price}€
          </Typography>
        )}
      </footer>
    </Card>
  );
};

export default InfraestructureCard;