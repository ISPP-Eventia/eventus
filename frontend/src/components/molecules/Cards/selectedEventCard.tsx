import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { Card, Typography } from "@mui/material";

import { EventUs } from "types";
import utils from "utils";
import { eventApi } from "api";

const SelectedEventCard = (props: { noPicture?: boolean }) => {
  const navigate = useNavigate();

  const eventId = Number(localStorage.getItem("eventId"));

  const { data: event } = useQuery("event", () =>
    eventApi.getEvent(eventId).then((response) => response.data as EventUs)
  );

  const onClick = () => {
    navigate(`/events/${eventId}`);
  };

  return (
    <Card
      className="grid w-full max-w-sm cursor-pointer grid-cols-5 border border-brand-lighter bg-brand-lighter shadow-sm hover:shadow-xl"
      onClick={onClick}
    >
      {!props.noPicture && (
        <header className="col-span-2">
          <img
            alt="img"
            className="h-28 w-full object-cover"
            src={
              event?.media?.[0]?.path || "https://via.placeholder.com/1000x500"
            }
          />
        </header>
      )}
      <section className="col-span-3 mt-2 mb-2 flex h-auto w-full flex-col justify-between gap-1 px-2">
        <div>
          {event?.title && <Typography variant="h5">{event?.title}</Typography>}
          {event?.description && (
            <p className="mt-1 mb-0">
              <Typography
                className="leading-[1.2] line-clamp-2"
                variant="subtitle1"
              >
                {event?.description}
              </Typography>
            </p>
          )}
        </div>
        <div className="flex w-full items-center justify-between">
          {event?.startDate && (
            <Typography variant="h6">
              {utils.formatters.formatDate(event?.startDate)}
            </Typography>
          )}
          {event?.price && (
            <Typography className="w-1/4" variant="h5">
              {event?.price}€
            </Typography>
          )}
        </div>
      </section>
    </Card>
  );
};

export default SelectedEventCard;
