import { tagApi } from "api";
import { useQuery } from "react-query";
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
    const { title, fromTo, price, description, media, tags } = eventFormValues;
    const [startDate, endDate] = fromTo;
    return {
      title,
      startDate: startDate.toISOString(),
      endDate: endDate.toISOString(),
      price,
      description,
      media: [],
      mediaIds: media.map((m) => m.id).join(","),
      tagsIds: tags.join(","),
    };
  },

  locationFormValuesToLocation: (
    locationFormValues: LocationFormValues
  ): Location => {
    const { name, price, description, longitude, latitude, media } =
      locationFormValues;
    return {
      name,
      coordinates: { latitude, longitude },
      price,
      description,
      media: [],
      mediaIds: media.map((m) => m.id).join(","),
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
  formatIds: (ids: number[]) => ids.join(","),
};

const facebookShareEndpoint = "https://www.facebook.com/sharer/sharer.php?u=";
const twitterShareEndpoint = "https://twitter.com/intent/tweet?text=";
const whatsappShareEndpoint = "https://wa.me/?text=";
const telegramShareEndpoint = `https://t.me/share/url?url=Eventus&text=`;
const mailShareEndpoint = "mailto:?subject=Eventus&body=";

const getDate = (dateString?: string) => {
  return `${dateString!.substring(8, 10)} / ${dateString!.substring(5, 7)}`;
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
  hashtag: (event: EventUs) => `#EventUs #${_.camelCase(event.title)}`,
  text: (socialMedia: SocialMedia, text: string) =>
    socialMedia !== "facebook"
      ? encodeURI(text).replaceAll("#", "%23")
      : window.location.href,
  share: (socialMedia: SocialMedia, text: string) =>
    window.open(share.endpoint(socialMedia) + share.text(socialMedia, text)),

  shareEvent: (socialMedia: SocialMedia, event: EventUs) => {
    const text = `
🙌 Estoy participando en el evento: 
🎪 ${event.title}
#️⃣ ${share.hashtag(event)}
📆 El día ${getDate(event.startDate)}
⏰ A las ${event.startDate!.substring(11, 16)}
💰 Precio: ${event.price}€

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
#️⃣ ${share.hashtag(event)}
📆 El día ${getDate(event.startDate)}
⏰ A las ${event.startDate!.substring(11, 16)}

🙌 Puedes patrocinar cualquier evento y darte a conocer en eventus.space 💸
    `;
    share.share(socialMedia, text);
  },
};

const date = {
  getDateYearsAgo: (years: number) => {
    const requestedDate = new Date();
    requestedDate.setFullYear(requestedDate.getFullYear() - years);
    return requestedDate;
  },
};

export const useTags = () => {
  return useQuery("recommendedTags", () => tagApi.getTags());
};

const utils = { parsers, formatters, share, date };

export default utils;
