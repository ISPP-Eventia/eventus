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
          src={
            props.location.media?.[0]?.path ||
            "https://via.placeholder.com/1000x500"
          }
        />
      </header>
      <section className="line-clamp-4 mt-2 mb-2 flex h-auto flex-col px-2">
        {props.location.name && (
          <Typography variant="h5">{props.location.name}</Typography>
        )}
        {props.location.description && (
          <p className="mt-1">
            <Typography variant="subtitle1">
              {props.location.description}
            </Typography>
          </p>
        )}
      </section>
      <footer className="mt-auto mb-1 flex flex-row items-center justify-between px-2">
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
