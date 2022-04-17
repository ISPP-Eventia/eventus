import { useEffect, useState } from "react";
import { Button, Typography } from "@mui/material";
import { useNavigate, useParams } from "react-router";
import { useQuery } from "react-query";

import { EventUs, Sponsorship, User } from "types";
import { eventApi } from "api";
import utils from "utils";

import { Ad, Loader, Map } from "components/atoms";
import { UserHorizontalCard } from "components/molecules";
import { ParticipateForm, SponsorshipForm } from "components/organisms";
import Page from "../page";
import ErrorPage from "pages/error";

import TwitterIcon from '@mui/icons-material/Twitter';
import { Facebook, Telegram, WhatsApp } from "@mui/icons-material";

const EventDetailPage = () => {
  const [twitterText, setTwitterText] = useState("");
  const [facebookText, setFacebookText] = useState("");
  const [whatsappText, setWhatsappText] = useState("");
  const [telegramText, setTelegramText] = useState("");

  


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

      
    const shareFacebook = () => {
      const text = "https://www.facebook.com/sharer/sharer.php?u=https://yoururl.com&t=your message"
      setFacebookText(text);
    }
    const shareTwitter = (event: EventUs) => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "🙌Estoy%20participando%20en%20el%20evento%20"+ event.title +"%0A📆El%20día%20"+ fecha + "%20a%20las%20"+ hora + "%20⏰%0A✅Tú%20también%20puedes%20inscribirte%20en%20el%20siguiente%20enlace%20➡%0A&url="+window.location.href+"";
      setTwitterText(text);
    }

    const shareWhatsapp = (event: EventUs) => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "https://wa.me/?text=Estoy%20participando%20en%20"+ event.title +"%0AEl%20día%20"+ fecha + "%20a%20las%20"+ hora + ".%0ATú%20también%20puedes%20inscribirte%20desde%20el%20siguiente%20enlace%20"+window.location.href+"";
      setWhatsappText(text);
    }

    const shareTelegram = (event: EventUs) => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "🙌Estoy%20participando%20en%20el%20evento%20"+ event.title +"%0A📆El%20día%20"+ fecha + "%20a%20las%20"+ hora + "%20⏰%0A✅Tú%20también%20puedes%20inscribirte%20en%20el%20siguiente%20enlace%20➡%0A&url="+ window.location.href;
      const fullLink = "https://t.me/share/url?text="+text+"";
      console.log(fullLink);
      setTelegramText(fullLink);
    }

    if(event !== undefined) {
      shareTwitter(event);
      shareFacebook();
      shareWhatsapp(event);
      shareTelegram(event);

    }

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
              <Button
                variant="contained"
                color="primary"
                onClick={() => navigate(`/events/${event.id}/edit`)}
              >
                Editar
              </Button>,
              <Button
                variant="contained"
                color="error"
                onClick={() =>
                  eventApi
                    .deleteEvent(event.id!)
                    .then(() => navigate("/events"))
                }
              >
                Eliminar
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
            <div className="shareButtons ml-8">
                <Typography variant="h4" className="font-bold">
                  Compartir
                </Typography>
                <a href={"https://twitter.com/intent/tweet?text="+twitterText+""} target="_blank"><TwitterIcon/></a>
                <a href={"https://www.facebook.com/sharer/sharer.php?u="+window.location.href} target="_blank"><Facebook/></a>
                <a href={whatsappText} target="_blank"><WhatsApp/></a>
                <a href={telegramText} target="_blank"><Telegram/></a>
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
