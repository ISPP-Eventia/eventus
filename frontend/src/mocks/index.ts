import { EventUs, Location, Sponsorship, User } from "types";

// USERS
export const DummyUser1: User = {
  id: 1,
  firstName: "John",
  lastName: "Doe",
  birthDate: "01/01/1990",
  email: "john@gmail.com",
};

export const DummyUser2: User = {
  id: 2,
  firstName: "Jane",
  lastName: "Doe",
  birthDate: "01/01/1990",
  email: "jane@gmail.com",
};

// LOCATIONS
export const DummyLocation1: Location = {
  id: 1,
  owner: DummyUser1,
  name: "Lorem Ipsum",
  description:
    "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quasi, quisquam.",
  coordinates: {
    latitude: 40.416775,
    longitude: -3.70379,
  },
  price: 4,
  media: [
    {
      id: 1,
      path: "https://media.istockphoto.com/photos/basketball-arena-picture-id466336640?k=20&m=466336640&s=612x612&w=0&h=RnIr5Y4d5C_zXyzkEQQPZ9Tf0X2JhuL39kaRhTmYKcY=",
    },
  ],
};

// EVENTS
export const DummyEvent1: EventUs = {
  id: 1,
  media: [
    {
      id: 1,
      path: "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
    },
  ],
  title: "Lorem Ipsum",
  description:
    "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quasi, quisquam. Quasi, quisquam. Some extra test text in the description, checking long descriptions line-clamp, so we need more text here, even more and more.",
  price: 4,
  startDate: "2019-01-16",
  organizer: DummyUser1,
  coordinates: DummyLocation1.coordinates,
};

export const DummyEvent2: EventUs = {
  id: 2,
  media: [
    {
      id: 1,
      path: "https://emerac.files.wordpress.com/2011/03/etsii.jpg?w=584",
    },
  ],
  title: "Test Text",
  description:
    "Test description, this content is not important, but it helps to test the component",
  price: 12,
  startDate: "2021-01-18",
  organizer: DummyUser1,
};

// SPONSORSHIPS
export const DummySponsorship1: Sponsorship = {
  id: 1,
  user: DummyUser1,
  quantity: 20,
  isAccepted: false,
  media: [
    {
      id: 1,
      path: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2s1MnBVr2klEkPS7reF7bgieU-H-acGccWg&usqp=CAU",
    },
  ],
};
