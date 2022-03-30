import { useEffect } from "react";
import { Button, Typography } from "@mui/material";
import { useNavigate, useParams } from "react-router";
import { useQuery } from "react-query";

import { EventUs, Sponsorship, User } from "types";
import { eventApi } from "api";
import utils from "utils";

import { Ad, Loader, Map } from "components/atoms";
import { ParticipateForm, SponsorshipForm } from "components/organisms";
import { UserHorizontalCard } from "components/molecules";
import Page from "../page";

const EventDetailPage = () => {
  const navigate = useNavigate();

  const eventId = Number(useParams().id);
  const loggedUserId = localStorage.getItem("userId");

  const { isLoading: loadingEvent, data: event } = useQuery("event", () =>
    eventApi.getEvent(eventId).then((response) => {
      return response.data as EventUs;
    })
  );

  const {
    isLoading: loadingParticipants,
    data: participants,
    refetch: refetchParticipants,
  } = useQuery("participants", () =>
    eventApi
      .getUsersByEvent(eventId)
      .then((response) => response.data as User[])
  );

  const {
    isLoading: loadingSponsorships,
    data: ads,
    refetch: refetchSponsorships,
  } = useQuery("sponsorships", () =>
    eventApi
      .getSponsorshipsByEvent(Number(eventId))
      .then((response) => response.data as Sponsorship[])
  );

  const onSearchLocation = () => {
    navigate("/locations");
  };

  useEffect(() => {
    localStorage.setItem("eventId", event?.id?.toString() ?? "1");
    refetchSponsorships();
    refetchParticipants();
  }, [event, refetchSponsorships, refetchParticipants]);

  return loadingEvent || !event ? (
    <Loader />
  ) : (
    <Page
      title={event.title}
      actions={
        event.organizer?.id === Number(loggedUserId)
          ? [
              <Button
                variant="contained"
                color="primary"
                onClick={() => navigate(`/events/${event.id}/edit`)}
              >
                Editar
              </Button>,
            ]
          : [
              <ParticipateForm event={event} callback={refetchParticipants} />,
              <SponsorshipForm event={event} callback={refetchSponsorships} />,
            ]
      }
    >
      <section className="mt-2 grid grid-cols-1 gap-x-8 gap-y-4 md:grid-cols-2 xl:mb-10 xl:grid-cols-4">
        <div className="col-span-1 flex flex-col xl:col-span-2">
          <img
            alt="img"
            className="w-full rounded-md object-cover"
            src={
              event?.media?.[0]?.path || "https://via.placeholder.com/2000x1000"
            }
          />
        </div>
        <div className="flex flex-col gap-3">
          <div>
            <Typography variant="h4">Organizador</Typography>
            <Typography variant="body1">
              {event?.organizer && (
                <UserHorizontalCard user={event?.organizer} />
              )}
            </Typography>
          </div>
          <div>
            <Typography variant="h4">Descripción</Typography>
            <Typography variant="body1">{event?.description}</Typography>
          </div>
          <div className="flex flex-col gap-y-3 md:flex-row md:gap-8 xl:gap-12">
            <div>
              <Typography variant="h4">Precio</Typography>
              <Typography variant="body1">{event?.price}€</Typography>
            </div>
            <div>
              <Typography variant="h4">Fecha</Typography>
              <Typography variant="body1">
                {utils.formatters.formatDateHour(event?.startDate ?? "")}
              </Typography>
              <Typography variant="body1">
                {utils.formatters.formatDateHour(event?.endDate ?? "")}
              </Typography>
            </div>
          </div>
        </div>
        {event?.coordinates ||
          (event.organizer?.id === Number(loggedUserId) && (
            <div className="flex flex-col md:col-span-2 xl:col-span-1">
              <Typography variant="h4">Ubicación</Typography>
              {event?.coordinates ? (
                <Map
                  lat={event?.coordinates.latitude}
                  lng={event?.coordinates.longitude}
                />
              ) : (
                <Button
                  variant="outlined"
                  color="primary"
                  onClick={onSearchLocation}
                >
                  Buscar alojamiento
                </Button>
              )}
            </div>
          ))}
      </section>

      {!loadingParticipants && !!participants?.length && (
        <section className="grid-cols-full mt-4 grid h-auto gap-x-8 gap-y-2">
          <Typography variant="h4">Participantes</Typography>
          <div className="grid h-auto grid-cols-1 gap-2 gap-x-8 gap-y-2 md:grid-cols-3 xl:grid-cols-4">
            {participants?.map((participant) => (
              <Typography variant="body1">
                {<UserHorizontalCard user={participant} />}
              </Typography>
            ))}
          </div>
        </section>
      )}

      {!loadingSponsorships &&
        !!ads?.filter((ad) => ad.isAccepted !== false).length && (
          <section className="grid-cols-full mt-4 grid h-auto gap-x-8 gap-y-2">
            <Typography variant="h4">Patrocinadores</Typography>
            <div className="grid h-auto grid-cols-1 gap-2 gap-x-8 gap-y-2 md:grid-cols-3 xl:grid-cols-4">
              {ads
                ?.filter((ad) => ad.isAccepted !== false)
                .map((ad) => (
                  <Ad callback={refetchSponsorships} sponsorship={ad} />
                ))}
            </div>
          </section>
        )}
    </Page>
  );
};

export default EventDetailPage;
