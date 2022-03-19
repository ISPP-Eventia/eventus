export type Participation = {
  userId: number;
  eventId: number;
  buyDate: string;
  ticket: string;
  price: number;
};

export type EventUs = {
  id: number;
  image?: string;
  title?: string;
  description?: string;
  price?: number;
  date?: string;
};

export type Sponsor = {
  user_Id : number;
  event_Id : number;
  quantity : number;
  name : string;
  is_accepted: boolean;
  //images: Image;
};