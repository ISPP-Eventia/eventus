// Utility types
export type Coordinates = {
  lat: number;
  lng: number;
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
  location?: Location;
  media?: Media[];
  startDate?: string;
  endDate?: string;
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

// Relation types
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

export type Hosting = {
  id?: number;
  eventId: number;
  isAccepted?: boolean;
};
