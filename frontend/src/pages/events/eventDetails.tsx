import { useEffect } from "react";
import { Button, IconButton, Typography } from "@mui/material";
import { useNavigate, useParams } from "react-router";
import { useQuery } from "react-query";
import { Delete, Edit } from "@mui/icons-material";

import { EventUs, Sponsorship, User } from "types";
import { eventApi } from "api";
import utils from "utils";

import { Ad, Loader, Map } from "components/atoms";
import { UserHorizontalCard } from "components/molecules";
import { ParticipateForm, SponsorshipForm } from "components/organisms";
import Page from "../page";
import ErrorPage from "pages/error";
import { ShareModal } from "components/templates";

import TwitterIcon from '@mui/icons-material/Twitter';
import { Facebook, Telegram, WhatsApp } from "@mui/icons-material";

const EventDetailPage = () => {
  const navigate = useNavigate();

  const eventId = Number(useParams().id);

  const loggedUserId = Number(localStorage.getItem("userId"));
  const isAdmin = localStorage.getItem("isAdmin");

  const {
    isLoading: loadingEvent,
    data: event,
    error: eventError,
    isError: isEventError,
  } = useQuery("event", () =>
    eventApi.getEvent(eventId).then((response) => {
      return response?.data as EventUs;
    })
  );

  const {
    isLoading: loadingParticipants,
    data: participants,
    refetch: refetchParticipants,
  } = useQuery("participants", () =>
    eventApi
      .getUsersByEvent(eventId)
      .then((response) => response?.data as User[])
  );

  const {
    isLoading: loadingSponsorships,
    data: ads,
    refetch: refetchSponsorships,
  } = useQuery("sponsorships", () =>
    eventApi
      .getSponsorshipsByEvent(Number(eventId))
      .then((response) => response?.data as Sponsorship[])
  );

  const onSearchLocation = () => {
    navigate("/locations");
  };

  useEffect(() => {
    if (
      event?.organizer?.id === loggedUserId &&
      !event?.coordinates &&
      eventId
    ) {
      localStorage.setItem("eventId", eventId.toString());
      
    } else localStorage.removeItem("eventId");

    refetchSponsorships();
    refetchParticipants();    
  }, [event, refetchSponsorships, refetchParticipants, loggedUserId, eventId]);

  return loadingEvent ? (
    <Loader />
  ) : isEventError || !event ? (
    <ErrorPage errorMessage={(eventError as Error)?.message || ""} />
  ) : (
    <Page
      title={event.title}
      actions={
        event.organizer?.id === loggedUserId || isAdmin === "true"
          ? [
              <IconButton
                color="primary"
                onClick={() => navigate(`/events/${event.id}/edit`)}
              >
                <Edit />
              </IconButton>,
              <IconButton
                color="error"
                onClick={() =>
                  eventApi
                    .deleteEvent(event.id!)
                    .then(() => navigate("/events"))
                }
              >
                <Delete />
              </IconButton>,
              <ShareModal type="event" entity={event} />,
            ]
          : [
              <ParticipateForm event={event} callback={refetchParticipants} />,
              <SponsorshipForm event={event} callback={refetchSponsorships} />,
              <ShareModal type="event" entity={event} />,
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
          <div className="flex flex-col gap-y-3 md:flex-row md:gap-8">
            <div>
              <Typography variant="h4">Precio</Typography>
              <Typography variant="h6">{event?.price}€</Typography>
              {event?.prize && (
                <div>
                  <Typography variant="h4">Premio</Typography>
                  <Typography variant="h6">{event?.prize}€</Typography>
                </div>
              )}
            </div>
            <div>
              <Typography variant="h4">Fecha</Typography>
              <Typography variant="h6" className="font-bold">
                {utils.formatters.formatDateHour(event?.startDate ?? "")}
              </Typography>
              <Typography variant="h6" className="font-bold">
                {utils.formatters.formatDateHour(event?.endDate ?? "")}
              </Typography>
              
            </div>
          </div>
          <div className="flex flex-col gap-y-3 md:flex-row md:gap-8"></div>
        </div>
        {!!event?.coordinates ||
          (event.organizer?.id === Number(loggedUserId) && (
            <div className="flex flex-col md:col-span-2 xl:col-span-1">
              <Typography variant="h4">Ubicación</Typography>
              {!!event?.coordinates ? (
                <Map
                  lat={event.coordinates?.latitude}
                  lng={event.coordinates?.longitude}
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
                .sort((a, b) => b.quantity - a.quantity)
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
