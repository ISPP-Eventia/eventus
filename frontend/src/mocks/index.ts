import { EventUs } from "types";
import { Infraestructure } from "types";


export const DummyEvent1: EventUs = {
  id: 1,
  image:
    "https://urbancolex.com/wp-content/uploads/2019/03/cancha-de-baloncesto-1024x576.jpg",
  title: "Pachanguita",
  description: "Unas partiditas",
  price: 5,
  date: "2019-01-16",
};

export const DummyEvent2: EventUs = {
  id: 2,
  image: "https://emerac.files.wordpress.com/2011/03/etsii.jpg?w=584",
  title: "Hacer trabajo ISPP",
  description: "Presentacion",
  price: 5,
  date: "2021-01-18",
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
