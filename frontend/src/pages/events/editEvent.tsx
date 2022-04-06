import { useParams } from "react-router";
import { useQuery } from "react-query";
import { eventApi } from "api";
import { EventUs } from "types";

import { Loader } from "components/atoms";
import EditEvent from "components/templates/editEvent/EditEvent";
import Page from "../page";

const EditEventPage = () => {
  const eventId = Number(useParams().id);

  const { isLoading: loadingEvent, data: event } = useQuery("event", () =>
    eventApi.getEvent(eventId).then((response) => {
      return response?.data as EventUs;
    })
  );

  return (
    <Page title="Nuevo Evento">
      {loadingEvent || !event ? <Loader /> : <EditEvent event={event} />}
    </Page>
  );
};

export default EditEventPage;
