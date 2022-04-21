import {
  EventFormValues,
  EventUs,
  Location,
  LocationFormValues,
  SignupFormValues,
  SocialMedia,
  Sponsorship,
  User,
} from "types";

var _ = require("lodash");

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
    return date.toLocaleString("Es-ES").replace(/:\d\d$/, "");
  },
};

const facebookShareEndpoint = "https://www.facebook.com/sharer/sharer.php?u=";
const twitterShareEndpoint = "https://twitter.com/intent/tweet?text=";
const whatsappShareEndpoint = "https://wa.me/?text=";
const telegramShareEndpoint = `https://t.me/share/url?url=Eventus&text=`;
const mailShareEndpoint = "mailto:?subject=Eventus&body=";

const getDate = (date?: string) => {
  return `${date!.substring(8, 10)} / ${date!.substring(5, 7)}`;
};

const share = {
  endpoint: (socialMedia: SocialMedia) => {
    switch (socialMedia) {
      case "facebook":
        return facebookShareEndpoint;
      case "twitter":
        return twitterShareEndpoint;
      case "whatsapp":
        return whatsappShareEndpoint;
      case "telegram":
        return telegramShareEndpoint;
      case "mail":
        return mailShareEndpoint;
    }
  },
  hashtag : (event: EventUs) => {
    if(event){
        return "#"+_.camelCase("#EventUS"+`${event.title}`+`${event.id}`);
    }
  },
  text: (socialMedia: SocialMedia, text: string) =>
    socialMedia !== "facebook" ? encodeURI(text) : window.location.href,
  share: (socialMedia: SocialMedia, text: string) =>
    window.open(share.endpoint(socialMedia) + share.text(socialMedia, text)),

  shareEvent: (socialMedia: SocialMedia, event: EventUs, hashtag: String) => {
    const text = `
🙌 Estoy participando en el evento: 
🎪 ${event.title}
📆 El día ${getDate(event.startDate)}
⏰ A las ${event.startDate!.substring(11, 16)}
💰 Precio: ${event.price}€
${hashtag}

🙌 Tú también puedes inscribirte aquí:
${window.location.href}
    `;
    share.share(socialMedia, text);
  },

  shareLocation: (socialMedia: SocialMedia, location: Location) => {
    const text = `
👀 Mira este alojamiento para eventos: 
🎪 ${location.name}
      
🙌 Puedes alojar cualquier evento por ${location.price} 💸 desde Eventus.
${window.location.href}
`;
    share.share(socialMedia, text);
  },

  shareSponsorship: (
    socialMedia: SocialMedia,
    sponsorship: Sponsorship,
    event: EventUs
  ) => {
    const text = `
🔥 ${sponsorship.name || "Alguien"} ha patrocinado el evento: 
🎪 ${event.title}
📆 El día ${getDate(event.startDate)}
⏰ A las ${event.startDate!.substring(11, 16)}

🙌 Puedes patrocinar cualquier evento y darte a conocer en eventus.space 💸
    `;
    share.share(socialMedia, text);
  },
};

const utils = { parsers, formatters, share };

export default utils;
