import { EventFormValues, EventUs } from "types";

const parsers = {
  eventusFormValuesToEventus: (eventFormValues: EventFormValues): EventUs => {
    const { title, fromTo, price, description } = eventFormValues;
    const [startDate, endDate] = fromTo;
    return {
      title,
      startDate: startDate.toISOString(),
      endDate: endDate.toISOString(),
      price,
      description,
      media: undefined,
    };
  },
};

const formatters = {
  formatDate: (s: string) => {
    const date = new Date(s);
    return date.toLocaleDateString("Es-ES");
  },
};

const utils = { parsers, formatters };

export default utils;
