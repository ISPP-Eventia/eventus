import { useNavigate } from "react-router";
import { Card, Typography } from "@mui/material";

import { Location } from "types";
import { useQuery } from "react-query";
import { mediaApi } from "api";

const LocationCard = (props: { location: Location }) => {
  const { location } = props;
  const navigate = useNavigate();

  const onClick = () => {
    navigate(`/locations/${props.location.id}`);
  };

  const mediaIdQuery = "media" + location.id;
  const { data: media } = useQuery(mediaIdQuery, async () => {
    if (!location || !location.media || location.media.length === 0)
      return {
        id: "0",
        title: "Placeholder",
        url: "https://via.placeholder.com/1000x500",
      };

    const url = await mediaApi.getMedia(location.media[0].id!);

    return {
      id: location.media[0].id,
      title: location.media[0].title,
      url,
    };
  });

  return (
    <Card
      className="flex cursor-pointer flex-col hover:shadow-xl"
      onClick={onClick}
    >
      <header className="w-full">
        <img
          alt={media?.title}
          className="h-28 w-full object-cover"
          src={media?.url}
        />
      </header>
      <section className="mt-2 mb-2 flex h-auto flex-col px-2 line-clamp-4">
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
