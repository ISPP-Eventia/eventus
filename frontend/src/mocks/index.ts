import { EventUs, Sponsorship, User, Infraestructure } from "types";

// USERS
export const DummyUser1: User = {
  id: 1,
  firstName: "John",
  lastName: "Doe",
  birthDate: "01/01/1990",
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
  isAccepted: true,
  media: [
    {
      path: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fes.dreamstime.com%2Fsponsor-azul-aleatorio-formas-horizontales-texto-del-patrocinador-escrito-sobre-fondo-image214088486&psig=AOvVaw2g82lf_GGSER6U4GxAKjxz&ust=1647803964329000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLifoqDy0vYCFQAAAAAdAAAAABAK",
    },
  ],
};

export const DummyInfraestructure1: Infraestructure = {
  id: 1,
  owner_id: 1,
  location: "av./ Hytasa 10",
  price: 5,
  image:
    "http://www.andaluciaesdeporte.org/sites/default/files/0000616302_560x560_jpg000.jpg",
};

export const DummyInfraestructure2: Infraestructure = {
  id: 1,
  owner_id: 1,
  location: "c/ Doctor Laffon Soto s/n",
  price: 5,
  image:
    "https://upload.wikimedia.org/wikipedia/commons/f/fd/Polideportivo_San_Pablo_%28Sevilla%29.jpg",
};
