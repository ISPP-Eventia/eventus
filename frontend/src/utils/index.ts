import {
  EventFormValues,
  EventUs,
  Location,
  LocationFormValues,
  SignupFormValues,
  User,
} from "types";

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
    const { name, price, description, longitude, latitude } =
      locationFormValues;
    return {
      name,
      coordinates: { latitude, longitude },
      price,
      description,
      media: undefined,
    };
  },

  signupFormValuesToUser: (signupFormValues: SignupFormValues): User => {
    const { email, password, firstName, lastName, birthDate } =
      signupFormValues;
    return {
      email,
      password,
      firstName,
      lastName,
      birthDate: birthDate.toISOString(),
    };
  },
};

const formatters = {
  formatDate: (s: string) => {
    const date = new Date(s);
    return date.toLocaleDateString("Es-ES");
  },
  formatDateHour: (s: string) => {
    const date = new Date(s);
    return date.toLocaleString("Es-ES");
  },
};

const utils = { parsers, formatters };

export default utils;
