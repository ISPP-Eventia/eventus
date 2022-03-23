import { EventFormValues, EventUs, Location, LocationFormValues } from "types";

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

  locationFormValuesToLocation: (
    locationFormValues: LocationFormValues
  ): Location => {
    const { name, price, description } = locationFormValues;
    return {
      name,
      location: JSON.stringify({ lat: 37.358273, lng: -5.986795 }),
      price,
      description,
      owner: 1,
      media: undefined,
    } as any;
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
