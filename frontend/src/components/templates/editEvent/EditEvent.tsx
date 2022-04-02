import { eventApi } from "api";
import { EventFormValues, EventUs } from "types";
import utils from "utils";

import { Error } from "components/atoms";
import { EventForm } from "components/organisms";
import { useNavigate } from "react-router";
import { useMemo, useState } from "react";
import moment from "moment";

export interface EditEventProps {
  event: EventUs;
}

const EditEvent = (props: EditEventProps) => {
  const { event } = props;
  const navigate = useNavigate();

  const [error, setError] = useState<string>("");
  const handleSubmit = (values: EventFormValues) => {
    const eventBody = utils.parsers.eventusFormValuesToEventus(values);
    eventBody.id = event.id;
    eventApi
      .updateEvent(eventBody)
      .then((r) => {
        navigate("/events");
      })
      .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
  };

  const initialValues: any = useMemo(
    () => ({
      ...event,
      fromTo: [moment(event.startDate ?? ""), moment(event.endDate ?? "")],
    }),
    [event]
  );

  return (
    <>
      <EventForm onSubmit={handleSubmit} initialValues={initialValues} />
      {error && <Error error={error} />}
    </>
  );
};

export default EditEvent;
