import EventForm from "components/organisms/forms/EventForm";
import { EventFormValues } from "types";

const NewEvent = () => {
  const handleSubmit = (values: EventFormValues) => {
    console.log(values);
  };
  return <EventForm onSubmit={handleSubmit} />;
};

export default NewEvent;
