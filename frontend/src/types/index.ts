// Utility types
export type Coordinates = {
  latitude: number;
  longitude: number;
};

export type Media = {
  id?: number;
  path: string;
  title?: string;
  description?: string;
};

// Main types
export type User = {
  id?: number;
  firstName: string;
  lastName: string;
  birthDate?: string;
  email?: string;
  password?: string;
};

export type EventUs = {
  id?: number;
  organizer?: User;
  title: string;
  description?: string;
  price: number;
  coordinates?: Coordinates;
  media?: Media[];
  startDate?: string;
  endDate?: string;
  prize?: number;
  rating?: number;
};

export type Location = {
  id?: number;
  owner?: User;
  name: string;
  description?: string;
  coordinates: Coordinates;
  price: number;
  media?: Media[];
};

// Relation types
export type Participation = {
  id?: number;
  buyDate: string;
  ticket: string;
  price: number;
  event: EventUs;
};

export type Sponsorship = {
  id?: number;
  eventId?: number;
  user?: User;
  quantity: number;
  isAccepted?: boolean;
  media?: Media[];
};

export type Hosting = {
  id?: number;
  eventId: number;
  locationId: number;
  price?: number;
  isAccepted?: boolean;
  event?: EventUs;
};

// Form values types

export type SponsorshipFormValues = {
  quantity: number;
};

export type EventFormValues = {
  title: string;
  fromTo: [Date, Date];
  price: number;
  description?: string;
};

export type LocationFormValues = {
  price: number;
  name: string;
  description: string;
  longitude: number;
  latitude: number;
};

export type LoginFormValues = {
  email: string;
  password: string;
};

export type SignupFormValues = {
  firstName: string;
  lastName: string;
  birthDate: Date;
  email: string;
  password: string;
};
