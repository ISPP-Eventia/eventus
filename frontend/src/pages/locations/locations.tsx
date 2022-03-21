import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router";
import { LocationCard } from "components/molecules";
import { DummyLocation1 } from "mocks";
import { Location } from "types";
import Page from "../page";

const LocationListPage = () => {
  const navigate = useNavigate();

  const [locations, setLocations] = React.useState<Location[]>();

  React.useEffect(() => {
    let isCancelled = false;

    // TODO: call api and get events

    !isCancelled &&
    setLocations([
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      DummyLocation1,
      ]);

    return () => {
      isCancelled = true;
    };
  }, []);

  const onNewLocationClick = () => {
    navigate("/locations/new");
  };

  const AddLocation = (
    <Button variant="contained" color="primary" onClick={onNewLocationClick}>
      New Infraestructure
    </Button>
  );

  return (
    <Page title="Localizacione disponibles" actions={AddLocation}>
      <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
        {locations?.map((e) => (
          <LocationCard location={e} />
        ))}
      </section>
    </Page>
  );
};

export default LocationListPage;