import { Button, Typography } from "@mui/material";
import { useNavigate } from "react-router";
import { useQuery } from "react-query";

import { Location } from "types";
import { locationApi } from "api";

import { LocationCard, SelectedEventCard } from "components/molecules";
import Page from "../page";

const LocationListPage = () => {
  const navigate = useNavigate();

  const { data: locations } = useQuery("locations", () =>
    locationApi.getLocations().then((response) => response.data as Location[])
  );

  const onNewLocationClick = () => {
    navigate("/locations/new");
  };

  const AddLocation = (
    <Button variant="contained" color="primary" onClick={onNewLocationClick}>
      Nueva Ubicación
    </Button>
  );
  return (
    <Page title="Ubicaciones disponibles" actions={AddLocation}>
      <div className="mt-6">
        <Typography variant="h4">Evento Seleccionado</Typography>
        <SelectedEventCard />
      </div>

      <div className="mt-6">
        <Typography variant="h4" className="mt-6">
          Ubicaciones
        </Typography>
        <section className="grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
          {locations?.map((e) => (
            <LocationCard location={e} />
          ))}
        </section>
      </div>
    </Page>
  );
};

export default LocationListPage;
