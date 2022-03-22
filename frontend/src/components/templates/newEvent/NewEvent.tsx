import { eventApi } from "api";
import EventForm from "components/organisms/forms/EventForm";
import { EventService } from "services/event/EventService";
import { EventFormValues } from "types";
import utils from "utils";

export interface NewEventProps {
  eventService?: EventService;
}
const NewEvent = (props: NewEventProps) => {
  const handleSubmit = (values: EventFormValues) => {
    const eventBody = utils.parsers.eventusFormValuesToEventus(values);
    eventApi.createEvent(eventBody);
  };

  return <EventForm onSubmit={handleSubmit} />;
};

export default NewEvent;
