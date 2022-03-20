import { EventUs, Sponsorship, User } from "types";

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

// EVENTS
export const DummyEvent1: EventUs = {
  id: 1,
  media: [
    {
      path: "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
    },
  ],
  title: "Lorem Ipsum",
  description:
    "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quasi, quisquam. Quasi, quisquam. Some extra test text in the description, checking long descriptions line-clamp, so we need more text here, even more and more.",
  price: 4,
  startDate: "2019-01-16",
  organizer: DummyUser1,
};

export const DummyEvent2: EventUs = {
  id: 2,
  media: [
    {
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
      path: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2s1MnBVr2klEkPS7reF7bgieU-H-acGccWg&usqp=CAU",
    },
  ],
};
