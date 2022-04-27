import { eventApi } from "api";
import { EventFormValues, EventUs } from "types";
import utils, { useTags } from "utils";

import { Error, Loader } from "components/atoms";
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
      .then(() => {
        navigate(-1);
      })
      .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
  };

  const initialValues: any = useMemo(
    () => ({
      ...event,
      fromTo: [moment(event.startDate ?? ""), moment(event.endDate ?? "")],
      tags: event.tags?.map((x: any) => x.id)
    }),
    [event]
  );

  const { isLoading, data } = useTags();

  return (
    <>
      {(isLoading || !data) ? (
        <Loader />
      ) : (
        <EventForm
          onSubmit={handleSubmit}
          initialValues={initialValues}
          tagsOptions={(data as any).data}
        />
      )}

      {error && <Error error={error} />}
    </>
  );
};

export default EditEvent;
