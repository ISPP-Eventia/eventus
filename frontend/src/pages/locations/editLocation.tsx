import { locationApi } from "api";
import { Loader } from "components/atoms";
import EditLocation from "components/templates/editLocation/EditLocation";
import { useQuery } from "react-query";
import { useParams } from "react-router";
import { Location } from "types";
import Page from "../page";

const EditLocationPage = () => {
  const locationId = Number(useParams().id);

  const { isLoading: loadingLocation, data: location } = useQuery(
    "location",
    () =>
      locationApi
        .getLocation(locationId)
        .then((response) => response.data as Location)
  );
  return (
    <Page title="Nueva Ubicación">
      {loadingLocation || !location ? (
        <Loader />
      ) : (
        <EditLocation location={location} />
      )}
    </Page>
  );
};

export default EditLocationPage;
