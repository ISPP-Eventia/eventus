import { Typography } from "@mui/material";
import { useParams } from "react-router";

import { Hosting, Location } from "types";

import { Loader, Map } from "components/atoms";
import { HostingForm } from "components/organisms";
import Page from "../page";
import { hostingApi, locationApi } from "api";
import { useQuery } from "react-query";
import { UserHorizontalCard } from "components/molecules";
import HostingRequest from "components/atoms/HostingRequest/HostingRequest";

const LocationDetailPage = () => {
  const locationId = Number(useParams().id);
  const eventId = Number(localStorage.getItem("eventId"));

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
  return isLoading || !location ? (
    <Loader />
  ) : (
    <Page
      title={location.name}
      actions={[<HostingForm hosting={hosting} onSubmit={refetchHostings} />]}
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
            <Typography variant="body1">{location.price}€ / h</Typography>
          </div>
        </div>
      </section>

      <section className="grid-cols-full mt-4 grid h-auto">
        <Typography variant="h4">Ubicación</Typography>
        <Map
          lat={location.coordinates.latitude}
          lng={location.coordinates.longitude}
        />
      </section>
      {!isLoadingHosting && !!hostings?.filter((hosting) => hosting.isAccepted !== false).length && (
        <section className="grid-cols-full mt-4 grid h-auto gap-x-8 gap-y-2">
          <Typography variant="h4">Alojamientos</Typography>
          <div className="grid h-auto grid-cols-1 gap-2 gap-x-8 gap-y-2 md:grid-cols-3 xl:grid-cols-4">
            {hostings
              .filter((hosting) => hosting.isAccepted !== false)
              .map((hosting) => (
                <HostingRequest callback={refetchHostings} hosting={hosting} />
              ))}
          </div>
        </section>
      )}
    </Page>
  );
};

export default LocationDetailPage;
