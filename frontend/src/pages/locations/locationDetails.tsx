import { useQuery } from "react-query";
import { useNavigate, useParams } from "react-router";
import { Button, Typography } from "@mui/material";

import { Hosting, Location } from "types";
import { hostingApi, locationApi } from "api";

import { Loader, Map, HostingRequest } from "components/atoms";
import { SelectedEventCard, UserHorizontalCard } from "components/molecules";
import { HostingForm } from "components/organisms";
import Page from "../page";

const LocationDetailPage = () => {
  const navigate = useNavigate();

  const locationId = Number(useParams().id);
  const eventId = Number(localStorage.getItem("eventId"));
  const loggedUserId = localStorage.getItem("userId");
  const isAdmin = localStorage.getItem("isAdmin");

  const { isLoading, data: location } = useQuery("location", () =>
    locationApi.getLocation(locationId).then((response) => {
      return response.data as Location;
    })
  );

  const {
    isLoading: isLoadingHosting,
    data: hostings,
    refetch: refetchHostings,
  } = useQuery("hosting", () =>
    hostingApi.getHostings(locationId).then((response) => {
      return response.data as Hosting[];
    })
  );

  const hosting: Hosting = {
    eventId,
    locationId,
    price: location?.price || 0,
  };

  const handleAutoHost = () => {
    hostingApi
      .createHosting({ eventId, locationId, price: 0 })
      .then((response) => refetchHostings());
  };

  return isLoading || !location ? (
    <Loader />
  ) : (
    <Page
      title={location.name}
      actions={
        (location.owner?.id === Number(loggedUserId) || isAdmin === 'true')
          ? [
              <Button
                variant="contained"
                color="primary"
                onClick={() => navigate(`/locations/${locationId}/edit`)}
              >
                Editar
              </Button>,
              eventId !== null ? (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={() => handleAutoHost()}
                >
                  Alojar mi evento
                </Button>
              ) : null,
              <Button
              variant="contained"
              color="error"
              onClick={() =>
                locationApi
                  .deleteLocation(location.id!)
                  .then(() => navigate("/locations"))
              }
            >
              Eliminar
            </Button>,
            ]
          : [
              eventId !== null && (
                <HostingForm hosting={hosting} onSubmit={refetchHostings} />
              ),
            ]
      }
    >
      <section className="mt-2 grid grid-cols-1 gap-x-8 gap-y-4 md:grid-cols-2 xl:mb-10 xl:grid-cols-4">
        <div className="col-span-1 flex flex-col xl:col-span-2">
          <img
            alt="img"
            className="w-full rounded-md object-cover"
            src={
              location.media?.[0]?.path ||
              "https://via.placeholder.com/2000x1000"
            }
          />
        </div>
        <div className="flex flex-col gap-3">
          <div>
            <Typography variant="h4">Propietario</Typography>
            <Typography variant="body1">
              {location.owner && <UserHorizontalCard user={location.owner} />}
            </Typography>
          </div>
          <div>
            <Typography variant="h4">Descripción</Typography>
            <Typography variant="body1">{location.description}</Typography>
          </div>
          <div>
            <Typography variant="h4">Precio</Typography>
            <Typography variant="h6">{location.price}€ / h</Typography>
          </div>
        </div>
        <div>
          <Typography variant="h4">Evento Seleccionado</Typography>
          <SelectedEventCard noPicture />
        </div>
      </section>

      <section className="mt-4 grid h-auto">
        <Typography variant="h4">Ubicación</Typography>
        <Map
          lat={location.coordinates.latitude}
          lng={location.coordinates.longitude}
        />
      </section>

      {!isLoadingHosting &&
        !!hostings?.filter((h) => h.isAccepted !== false).length && (
          <section className="mt-4 grid h-auto gap-x-8 gap-y-2">
            <Typography variant="h4">Alojamientos</Typography>
            <div className="grid h-auto grid-cols-1 gap-2 gap-x-8 gap-y-2 md:grid-cols-3 xl:grid-cols-4">
              {hostings
                .filter((h) => h.isAccepted !== false)
                .map((h) => (
                  <HostingRequest callback={refetchHostings} hosting={h} />
                ))}
            </div>
          </section>
        )}

      <section className="mt-4 grid h-auto"></section>
    </Page>
  );
};

export default LocationDetailPage;
