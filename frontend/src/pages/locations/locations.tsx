import React from "react";

import { DummyLocation1 } from "mocks";
import { Location } from "types";
import { Button } from "@mui/material";

import { LocationCard } from "components/molecules";
import Page from "../page";
import { useNavigate } from "react-router";
import { useQuery } from "react-query";
import { locationApi } from "api";

const LocationListPage = () => {
  const navigate = useNavigate();

  const { isLoading, data: locations } = useQuery("locations", () =>
    locationApi.getLocations().then((response) => response.data as Location[])
  );

  const onNewLocationClick = () => {
    navigate("/locations/new");
  };

  const AddLocation = (
    <Button variant="contained" color="primary" onClick={onNewLocationClick}>
      New Location
    </Button>
  );
  return (
    <Page title="Available Locations" actions={AddLocation}>
      <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
        {locations?.map((e) => (
          <LocationCard location={e} />
        ))}
      </section>
    </Page>
  );
};

export default LocationListPage;
