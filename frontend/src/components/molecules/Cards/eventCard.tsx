import { useNavigate } from "react-router";
import { Card, Typography } from "@mui/material";

import { EventUs } from "types";
import utils from "utils";
import { useQuery } from "react-query";
import { mediaApi } from "api";

const EventCard = (props: { event: EventUs }) => {
  const { event } = props;
  const navigate = useNavigate();

  const onClick = () => {
    navigate(`/events/${props.event.id}`);
  };

  const mediaIdQuery = "media" + event.id;
  const { data: media } = useQuery(mediaIdQuery, async () => {
    if (!event || !event.media || event.media.length === 0)
      return {
        id: "0",
        title: "Placeholder",
        url: "https://via.placeholder.com/1000x500",
      };

    const url = await mediaApi.getMedia(event.media[0].id!);

    return {
      id: event.media[0].id,
      title: event.media[0].title,
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
        {props.event.title && (
          <Typography variant="h4">{props.event.title}</Typography>
        )}
        {props.event.description && (
          <p className="mt-1">
            <Typography variant="subtitle1">
              {props.event.description}
            </Typography>
          </p>
        )}
      </section>
      <footer className="mt-auto mb-1 flex flex-row items-center justify-between px-2">
        {props.event.price && (
          <Typography className="w-1/4" variant="h5">
            {props.event.price}€
          </Typography>
        )}
        {props.event.startDate && (
          <Typography variant="h6">
            {utils.formatters.formatDate(props.event.startDate)}
          </Typography>
        )}
      </footer>
    </Card>
  );
};

export default EventCard;
