import { useMemo, useState } from "react";
import { locationApi } from "api";
import { Location, LocationFormValues } from "types";
import utils from "utils";

import { Error } from "components/atoms";
import LocationForm from "components/organisms/forms/locationForm";
import { useNavigate } from "react-router";

export interface EditLocationProps {
  location: Location;
}

const EditLocation = (props: EditLocationProps) => {
  const { location } = props;
  const navigate = useNavigate();

  const [error, setError] = useState("");
  const handleSubmit = (values: LocationFormValues) => {
    const locationBody = utils.parsers.locationFormValuesToLocation(values);
    locationBody.id = location.id;
    locationApi
      .updateLocation(locationBody)
      .then(() => {
        navigate(-1);
      })
      .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
  };
  const initialValues: any = useMemo(
    () => ({
      ...location,
      latitude: location.coordinates.latitude,
      longitude: location.coordinates.longitude,
    }),
    [location]
  );

  return (
    <>
      <LocationForm onSubmit={handleSubmit} initialValues={initialValues} />
      {error && <Error error={error} />}
    </>
  );
};

export default EditLocation;
