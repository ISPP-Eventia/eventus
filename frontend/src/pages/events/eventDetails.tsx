import React from "react";
import { Button, Typography } from "@mui/material";
import { useNavigate, useParams } from "react-router";

import { DummyEvent1, DummySponsorship1, DummyUser2 } from "mocks";
import { EventUs, Sponsorship, User } from "types";

import { Ad, Loader, Map } from "components/atoms";
import { UserHorizontalCard } from "components/molecules";
import { ParticipateForm } from "components/organisms";
import Page from "../page";

const EventDetailPage = () => {
  const navigate = useNavigate();

  const eventId = useParams().id;
  const [event, setEvent] = React.useState<EventUs>();
  const [refresh, setRefresh] = React.useState(false);

  const [participants, setParticipants] = React.useState<User[]>();
  const [ads, setAds] = React.useState<Sponsorship[]>();

  React.useEffect(() => {
    let isCancelled = false;

    if (!isCancelled) {
      // TODO: call API
      console.log(eventId);
      setEvent(DummyEvent1);
      setParticipants([DummyUser2, DummyUser2, DummyUser2]);
      setAds([
        DummySponsorship1,
        DummySponsorship1,
        DummySponsorship1,
        DummySponsorship1,
        DummySponsorship1,
      ]);
    }

    return () => {
      isCancelled = true;
    };
  }, [eventId, refresh]);

  const onSearchLocation = () => {
    navigate("/locations");
  };

  const isLoading = !event || !participants || !ads;

  return isLoading ? (
    <Loader />
  ) : (
    <Page title={event.title} actions={[<ParticipateForm event={event} />]}>
      <section className="mt-2 grid grid-cols-1 gap-x-8 gap-y-4 md:grid-cols-2 xl:mb-10 xl:grid-cols-4">
        <div className="col-span-1 flex flex-col xl:col-span-2">
          <img
            alt="img"
            className="w-full rounded-md object-cover"
            src={event.media?.[0]?.path}
          />
        </div>
        <div className="flex flex-col gap-3">
          <div>
            <Typography variant="h4">Organizer</Typography>
            <Typography variant="body1">
              {event.organizer && <UserHorizontalCard user={event.organizer} />}
            </Typography>
          </div>
          <div>
            <Typography variant="h4">Description</Typography>
            <Typography variant="body1">{event.description}</Typography>
          </div>
          <div className="flex flex-col md:flex-row md:gap-8 xl:gap-12">
            <div>
              <Typography variant="h4">Price</Typography>
              <Typography variant="body1">{event.price}€</Typography>
            </div>
            <div>
              <Typography variant="h4">Date</Typography>
              <Typography variant="body1">{event.startDate}</Typography>
              <Typography variant="body1">{event.endDate}</Typography>
            </div>
          </div>
        </div>
        <div className="flex flex-col md:col-span-2 xl:col-span-1">
          <Typography variant="h4">Location</Typography>
          {event.location ? (
            <Map
              lat={event.location.location.lat}
              lng={event.location.location.lng}
            />
          ) : (
            <Button
              variant="outlined"
              color="primary"
              onClick={onSearchLocation}
            >
              Look for a location
            </Button>
          )}
        </div>
      </section>

      <section className="grid-cols-full mt-4 grid h-auto gap-x-8 gap-y-2">
        <Typography variant="h4">Participants</Typography>
        <div className="grid h-auto grid-cols-1 gap-2 gap-x-8 gap-y-2 md:grid-cols-3 xl:grid-cols-4">
          {participants?.map((participant) => (
            <Typography variant="body1">
              {<UserHorizontalCard user={participant} />}
            </Typography>
          ))}
        </div>
      </section>

      <section className="grid-cols-full mt-4 grid h-auto gap-x-8 gap-y-2">
        <Typography variant="h4">Sponsors</Typography>
        <div className="grid h-auto grid-cols-1 gap-2 gap-x-8 gap-y-2 md:grid-cols-3 xl:grid-cols-4">
          {ads.map((ad) => (
            <Ad callback={() => setRefresh(!refresh)} sponsorship={ad} />
          ))}
        </div>
      </section>
    </Page>
  );
};

export default EventDetailPage;
