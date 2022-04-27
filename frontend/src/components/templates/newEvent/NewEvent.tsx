import { eventApi } from "api";
import { EventFormValues } from "types";
import utils, { useTags } from "utils";

import { Error, Loader } from "components/atoms";
import { EventForm } from "components/organisms";
import { useNavigate } from "react-router";
import { useState } from "react";

const NewEvent = () => {
  const navigate = useNavigate();

  const [error, setError] = useState<string>("");
  const handleSubmit = (values: EventFormValues) => {
    const eventBody = utils.parsers.eventusFormValuesToEventus(values);
    eventApi
      .createEvent(eventBody)
      .then(() => {
        navigate(-1);
      })
      .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
  };

  const { isLoading, data } = useTags();
  return (
    <>
      {isLoading || !data ? (
        <Loader />
      ) : (
        <EventForm
          onSubmit={handleSubmit}
          tagsOptions={(data as any).data}
        />
      )}

      {error && <Error error={error} />}
    </>
  );
};

export default NewEvent;
