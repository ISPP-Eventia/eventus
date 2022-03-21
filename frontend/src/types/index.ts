export type EventUs = {
  id?: number;
  organizer?: User;
  title: string;
  description?: string;
  price: number;
  location?: Coordinates;
  media?: Media[];
  startDate?: string;
  endDate?: string;
};

export type User = {
  id?: number;
  firstName: string;
  lastName: string;
  birthDate?: string;
  email?: string;
  password?: string;
};

export type Media = {
  id?: number;
  path: string;
  title?: string;
  description?: string;
};

export type Participation = {
  id?: number;
  buyDate: string;
  ticket: string;
  price: number;
};

export type Sponsorship = {
  id?: number;
  user?: User;
  quantity: number;
  isAccepted?: boolean;
  media?: Media[];
};

export type Location = {
  id?: number;
  owner?: User;
  name: string;
  description?: string;
  location: Coordinates;
  price: number;
  media?: Media[];
};

export type Hosting = {
  id?: number;
  eventId: number;
  isAccepted?: boolean;
};

export type Coordinates = {
  lat: number;
  lng: number;
};

export type EventFormValues = {
  title: string;
  fromTo: [Date, Date];
  price: number;
  description?: string;
};

export type EventDataBody = {
  title: string;
  startDate: string;
  endDate: string;
  price: number;
  description?: string;
};
