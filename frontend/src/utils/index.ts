import { EventFormValues } from "types";

const parsers = {
  eventusFormValuesToEventus: (eventFormValues: EventFormValues) => {
    const { title, fromTo, price, description } = eventFormValues;
    const [startDate, endDate] = fromTo;
    return {
      title,
      startDate: startDate.toISOString(),
      endDate: endDate.toISOString(),
      price,
      description,
    };
  },
};

const utils = { parsers };

export default utils;
