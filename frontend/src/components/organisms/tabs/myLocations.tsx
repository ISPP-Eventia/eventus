import { useQuery } from "react-query";
import { Link } from "react-router-dom";
import { Typography } from "@mui/material";

import { Location } from "types";
import { userApi } from "api";

import { Loader } from "components/atoms";
import { LocationCard } from "components/molecules";

const MyLocationsTab = () => {
  const { isLoading, data: locations } = useQuery("locations", () =>
    userApi
      .getLocationsByOwner()
      .then((response) => response?.data as Location[])
  );

  return (
    <section>
      <Typography variant="h4">Mis Ubicaciones</Typography>
      {isLoading ? (
        <Loader />
      ) : locations?.length === 0 ? (
        <div>
          No tiene ninguna ubicación, <Link to="/locations/new">crea una!</Link>
        </div>
      ) : (
        <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {locations?.map((location) => (
            <LocationCard location={location} />
          ))}
        </section>
      )}
    </section>
  );
};

export default MyLocationsTab;
