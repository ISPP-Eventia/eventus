import React from "react";
import { Typography } from "@mui/material";
import { useParams } from "react-router";

import { DummyLocation1 } from "mocks";
import { Location } from "types";

import { Loader, Map } from "components/atoms";
import { HostingForm } from "components/organisms";
import Page from "../page";
import { locationApi } from "api";
import { useQuery } from "react-query";
import { UserHorizontalCard } from "components/molecules";

const LocationDetailPage = () => {
  const locationId = Number(useParams().id);

  const { isLoading, data: location } = useQuery("location", () =>
    locationApi.getLocation(locationId).then((response) => {
      return response.data as Location;
    })
  );

  return isLoading || !location ? (
    <Loader />
  ) : (
    <Page title={location.name} actions={[<HostingForm location={location} />]}>
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
            <Typography variant="h4">Owner</Typography>
            <Typography variant="body1">
              {location.owner && <UserHorizontalCard user={location.owner} />}
            </Typography>
          </div>
          <div>
            <Typography variant="h4">Description</Typography>
            <Typography variant="body1">{location.description}</Typography>
          </div>
          <div>
            <Typography variant="h4">Price</Typography>
            <Typography variant="body1">{location.price}€ / h</Typography>
          </div>
        </div>
      </section>

      <section className="grid-cols-full mt-4 grid h-auto">
        <Typography variant="h4">Location</Typography>
        <Map lat={37.358273} lng={-5.986795} />
      </section>
    </Page>
  );
};

export default LocationDetailPage;
