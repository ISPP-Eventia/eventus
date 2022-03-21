import React from "react";
import { Typography } from "@mui/material";
import { useParams } from "react-router";

import { DummyLocation1 } from "mocks";
import { Location } from "types";

import { Loader, Map } from "components/atoms";
import { HostingForm } from "components/organisms";
import Page from "../page";

const LocationDetailPage = () => {
  const locationId = useParams().id;
  const [location, setLocation] = React.useState<Location>();

  React.useEffect(() => {
    let isCancelled = false;

    if (!isCancelled) {
      // TODO: call API
      console.log(locationId);
      setLocation(DummyLocation1);
    }

    return () => {
      isCancelled = true;
    };
  }, [locationId]);

  const isLoading = !location;

  return isLoading ? (
    <Loader />
  ) : (
    <Page title={location.name} actions={[<HostingForm location={location} />]}>
      <section className="mt-2 grid grid-cols-1 gap-x-8 gap-y-4 md:grid-cols-2 xl:mb-10 xl:grid-cols-4">
        <div className="col-span-1 flex flex-col xl:col-span-2">
          <img
            alt="img"
            className="w-full rounded-md object-cover"
            src={location.media?.[0]?.path}
          />
        </div>
        <div className="flex flex-col gap-3">
          <div>
            <Typography variant="h4">Owner</Typography>
            <Typography variant="body1">{location.owner?.firstName}</Typography>
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
