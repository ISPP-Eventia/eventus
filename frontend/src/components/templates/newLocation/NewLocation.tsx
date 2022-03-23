import React from "react";
import { eventApi, locationApi } from "api";
import { LocationFormValues } from "types";
import utils from "utils";

import { Error } from "components/atoms";
import LocationForm from "components/organisms/forms/locationForm";
import { useNavigate } from "react-router";

const NewLocation = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState("");
  const handleSubmit = (values: LocationFormValues) => {
    const locationBody = utils.parsers.locationFormValuesToLocation(values);
    locationApi
      .createLocation(locationBody)
      .then(() => {
        navigate("/locations");
      })
      .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
  };

  return (
    <>
      <LocationForm onSubmit={handleSubmit} />
      {error && <Error error={error} />}
    </>
  );
};

export default NewLocation;
