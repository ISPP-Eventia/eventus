import React from "react";
import { eventApi } from "api";
import { EventFormValues } from "types";
import utils from "utils";

import { Error } from "components/atoms";
import { EventForm } from "components/organisms";
import { useNavigate } from "react-router";

const NewEvent = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState<string>("");
  const handleSubmit = (values: EventFormValues) => {
    const eventBody = utils.parsers.eventusFormValuesToEventus(values);
    eventApi
      .createEvent(eventBody)
      .then(() => {
        navigate("/events");
      })
      .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
  };

  return (
    <>
      <EventForm onSubmit={handleSubmit} />
      {error && <Error error={error} />}
    </>
  );
};

export default NewEvent;
