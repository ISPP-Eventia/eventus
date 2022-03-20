import React from "react";
import { Typography } from "@mui/material";
import { useParams } from "react-router";

import { DummyEvent1, DummySponsorship1, DummyUser1 } from "mocks";
import { EventUs, Sponsorship, User } from "types";

import { Ad, Loader, Map } from "components/atoms";
import { ParticipateForm, SponsorshipForm } from "components/organisms";
import Page from "../page";

const EventDetailPage = () => {
  const eventId = useParams().id;
  const [event, setEvent] = React.useState<EventUs>();

  const [participants, setParticipants] = React.useState<User[]>();
  const [ads, setAds] = React.useState<Sponsorship[]>();

  React.useEffect(() => {
    let isCancelled = false;

    if (!isCancelled) {
      // TODO: call API
      console.log(eventId);
      setEvent(DummyEvent1);
      setParticipants([DummyUser1, DummyUser1, DummyUser1]);
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
  }, [eventId]);

  const isLoading = !event || !participants || !ads;

  return isLoading ? (
    <Loader />
  ) : (
    <Page title={event.title} actions={[<ParticipateForm event={event} />, <SponsorshipForm event={event}/>]}>
      <section className="mt-2 mb-10 grid grid-cols-1 gap-x-8 gap-y-4 md:grid-cols-2 xl:grid-cols-4">
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
              {event.organizer?.firstName}
            </Typography>
          </div>
          <div>
            <Typography variant="h4">Description</Typography>
            <Typography variant="body1">{event.description}</Typography>
          </div>
          <div className="flex flex-col gap-5 md:flex-row">
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
          <Map lat={37.358273} lng={-5.986795} />
        </div>
      </section>

      <section className="mt-4 grid grid-cols-1 gap-x-8 gap-y-4 md:grid-cols-3 xl:grid-cols-4">
        <div className="flex flex-col gap-2">
          <Typography variant="h4">Participants</Typography>
          {participants?.map((participant) => (
            <Typography variant="body1">{participant.firstName}</Typography>
          ))}
        </div>
        <div className="flex flex-col gap-2">
          <Typography variant="h4">Sponsors</Typography>
          {ads.map((ad) => (
            <Typography variant="body1">{ad?.user?.firstName}</Typography>
          ))}
        </div>
        <div className="col-span-1 grid h-auto grid-cols-1 flex-col gap-2 gap-x-8 gap-y-2 md:flex-row xl:col-span-2 xl:grid-cols-2">
          {ads.map((ad) => (
            <Ad />
          ))}
        </div>
      </section>
    </Page>
  );
};

export default EventDetailPage;
