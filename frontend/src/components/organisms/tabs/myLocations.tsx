import { useNavigate } from "react-router";
import { useQuery } from "react-query";

import { Location } from "types";
import { locationApi } from "api";

import { Loader } from "components/atoms";
import { LocationCard } from "components/molecules";

const MyLocationsTab = () => {
    
    const { isLoading, data: locations } = useQuery("events", () =>
    locationApi.getLocations().then((response) => response.data as Location[])
    );


 
    return <section>
        {isLoading ? (
        <Loader />
      ) : (
        <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
          {locations?.map((e) => (
            <LocationCard location={e} />
          ))}
        </section>
      )}
    </section>;
};

  
export default MyLocationsTab;