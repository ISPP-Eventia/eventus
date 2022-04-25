import { useRef } from "react";
import { Link } from "react-router-dom";
import { IconButton, Typography } from "@mui/material";
import {
  CopyAll,
  Facebook,
  Mail,
  Share,
  Telegram,
  Twitter,
  WhatsApp,
} from "@mui/icons-material";

import utils from "utils";
import { SocialMedia } from "types";

import { ModalDrawer } from "components/organisms";

const Component = (props: {
  type: "event" | "location" | "sponsorship";
  entity: any;
}) => {
  const closeModalRef = useRef<any>(null);

  const onShare = (socialMedia: SocialMedia) => {
    switch (props.type) {
      case "event":
        utils.share.shareEvent(socialMedia, props.entity);
        break;
      case "location":
        utils.share.shareLocation(socialMedia, props.entity);
        break;
      case "sponsorship":
        utils.share.shareSponsorship(
          socialMedia,
          props.entity.sponsorship,
          props.entity.event
        );
        break;
    }
  };

  return (
    <ModalDrawer
      title={"Compartir " + props.type}
      opener={{
        color: "primary",
        icon: <Share />,
      }}
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      <div className="flex flex-col items-start gap-2">
        <Typography variant="h5" className="text-center">
          Compartir en:
        </Typography>
        <div className="flex w-full flex-wrap justify-center gap-x-5 gap-y-2">
          <IconButton size="large" onClick={() => onShare("twitter")}>
            <Twitter />
          </IconButton>
          <IconButton size="large" onClick={() => onShare("facebook")}>
            <Facebook />
          </IconButton>
          <IconButton size="large" onClick={() => onShare("whatsapp")}>
            <WhatsApp />
          </IconButton>
          <IconButton size="large" onClick={() => onShare("telegram")}>
            <Telegram />
          </IconButton>
          <IconButton size="large" onClick={() => onShare("mail")}>
            <Mail />
          </IconButton>
        </div>
        {props.type === "event" && (
          <>
            <Typography variant="h5" className="text-center">
              Usa el hashtag:
            </Typography>

            <Typography variant="h6" className="text-center">
              <span className="text-brand">
                {utils.share.hashtag(props.entity)}
              </span>
            </Typography>
          </>
        )}
        <Typography variant="h5" className="text-center">
          Comparte el enlace:
        </Typography>
        <div className="flex w-full items-center justify-between">
          <Typography variant="h6" className="text-center">
            <Link to={window.location.href}>{window.location.href}</Link>
          </Typography>
          <IconButton
            size="large"
            onClick={() => {
              navigator.clipboard.writeText(window.location.href);
            }}
          >
            <CopyAll />
          </IconButton>
        </div>
      </div>
    </ModalDrawer>
  );
};

export default Component;
