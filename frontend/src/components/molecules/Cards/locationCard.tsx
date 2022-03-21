import React from "react";
import { useNavigate } from "react-router";
import { Card, Typography } from "@mui/material";

import { Location } from "types";

const LocationCard = (props: { location: Location }) => {
  const navigate = useNavigate();

  const onClick = () => {
    navigate(`/locations/${props.location.id}`);
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
          src={props.location.media?.[0]?.path}
        />
      </header>
      <section className="mt-2 flex h-44 flex-col px-2">
        {props.location.name && (
          <Typography variant="h5">{props.location.name}</Typography>
        )}
      </section>
      <footer className="mt-auto flex flex-row items-center justify-between px-2">
        {props.location.price && (
          <Typography className="w-1/4" variant="h6">
            {props.location.price}€
          </Typography>
        )}
      </footer>
    </Card>
  );
};

export default LocationCard;