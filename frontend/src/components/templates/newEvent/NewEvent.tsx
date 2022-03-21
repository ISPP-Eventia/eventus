import EventForm from "components/organisms/forms/EventForm";
import { EventService } from "services/event/EventService";
import { eventServiceImpl } from "services/event/EventServiceImpl";
import { EventFormValues } from "types";
import { convertEventValueToRequestBody } from "utils";

export interface NewEventProps {
  eventService?: EventService;
}
const NewEvent = (props: NewEventProps) => {
  const { eventService = eventServiceImpl } = props;

  const handleSubmit = (values: EventFormValues) => {
    const eventBody = convertEventValueToRequestBody(values);
    eventService.createEvent(eventBody);
  };
  return <EventForm onSubmit={handleSubmit} />;
};

export default NewEvent;
