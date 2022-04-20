import {
  EventFormValues,
  EventUs,
  Location,
  LocationFormValues,
  SignupFormValues,
  SocialMedia,
  User,
} from "types";

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

const share = {
  shareEvent: (socialMedia: SocialMedia, event: EventUs) => {  
    const shareFacebook = () => {
      const text = "https://www.facebook.com/sharer/sharer.php?u="+window.location.href;
      window.open(text);
    }
    const shareTwitter = () => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "https://twitter.com/intent/tweet?text=🙌Estoy%20participando%20en%20el%20evento%20"+ event.title +"%0A📆El%20día%20"+ fecha + "%20a%20las%20"+ hora + "%20⏰%0A✅Tú%20también%20puedes%20inscribirte%20en%20el%20siguiente%20enlace%20➡%0A&url="+window.location.href+"";
      window.open(text);
    }

    const shareWhatsapp = () => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "https://wa.me/?text=Estoy%20participando%20en%20"+ event.title +"%0AEl%20día%20"+ fecha + "%20a%20las%20"+ hora + ".%0ATú%20también%20puedes%20inscribirte%20desde%20el%20siguiente%20enlace%20"+window.location.href+"";
      window.open(text);
    }

    const shareTelegram = () => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "🙌Estoy%20participando%20en%20el%20evento%20"+ event.title +"%0A📆El%20día%20"+ fecha + "%20a%20las%20"+ hora + "%20⏰%0A✅Tú%20también%20puedes%20inscribirte%20en%20el%20siguiente%20enlace%20➡%0A&url="+ window.location.href;
      const fullLink = "https://t.me/share/url?text="+text+"";
      window.open(fullLink);
    }

    const shareMail = () => {
      const fecha = event.startDate!.substring(8,10)+"-"+event.startDate!.substring(5,7);
      const hora = event.startDate!.substring(11,16);
      const text = "mailto:?subject=¡Mira este evento: "+event.title+"! &body=📆El%20día%20"+ fecha + "%20a%20las%20"+ hora + "%20⏰%0A✅Tú%20también%20puedes%20inscribirte%20en%20el%20siguiente%20enlace%20desde%20Eventus%20➡%0A"+ window.location.href;
      window.open(text);
    }

    switch(socialMedia) {
      case ("twitter"):
        shareTwitter();
        break;
      case ("facebook"):
        shareFacebook();
        break;
      case ("whatsapp"):
        shareWhatsapp();
        break;
      case ("telegram"):
        shareTelegram();
        break;
      case ("mail"):
        shareMail();
        break;
    }
  },

  shareLocation: (socialMedia: SocialMedia, event: any) => {
    // TODO
  },
  shareSponsorship: (socialMedia: SocialMedia, event: any) => {
    // TODO
  }
};

const utils = { parsers, formatters, share };

export default utils;
