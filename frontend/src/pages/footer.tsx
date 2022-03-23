import { ReactElement } from "react";

import {
  Facebook,
  Instagram,
  LocationCity,
  Mail,
  Twitter,
} from "@mui/icons-material";
import { Divider, Typography } from "@mui/material";

const FooterSection = (props: {
  title: string;
  children: ReactElement | ReactElement[];
}) => {
  return (
    <section className="col-span-1">
      <Typography variant="h5">{props.title}</Typography>
      <Divider />
      <div className="mt-4 flex flex-col gap-4">{props.children}</div>
    </section>
  );
};

const AppFooter = () => {
  return (
    <footer className="bottom-0 grid w-full grid-cols-1 gap-10 rounded-t-xl bg-brand-lighter bg-opacity-50 px-4 py-8 md:grid-cols-3 md:px-8 lg:px-24 xl:gap-16 xl:px-48">
      <FooterSection title="Ubicación">
        <Typography variant="body2">
          Escuela Técnica Superior de Ingeniería Informática, Universidad de
          Sevilla, 41012 Sevilla
        </Typography>
        <a
          href="https://goo.gl/maps/S4zvin8BscVjHyRW6"
          className="flex items-center gap-2"
        >
          <LocationCity />
          <Typography variant="body2">Avenida Reina Mercedes S/N</Typography>
        </a>
        <Typography variant="body2">41012, Sevilla</Typography>
      </FooterSection>

      <FooterSection title="Contacto">
        <a href="mailto:info@eventus.space">
          <Mail /> info@eventus.space
        </a>
        <a href="mailto:josmonnie1@alum.us.es">
          <Mail /> josmonnie1@alum.us.es
        </a>
      </FooterSection>

      <FooterSection title="Siguenos">
        <a href="https://twitter.com/eventus_space">
          <Twitter /> @eventus_space
        </a>
        <a href="https://www.facebook.com/Eventus-107942035177629">
          <Facebook /> @eventus
        </a>
        <a href="https://www.instagram.com/eventus_space">
          <Instagram /> @eventus_space
        </a>
      </FooterSection>
    </footer>
  );
};

export default AppFooter;
