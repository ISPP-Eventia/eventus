import { useRef } from "react";
import { IconButton } from "@mui/material";
import { Facebook, Mail, Share, Telegram, Twitter, WhatsApp } from "@mui/icons-material";

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
        utils.share.shareSponsorship(socialMedia, props.entity);
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
      <div className="flex gap-5">
        <IconButton onClick={() => onShare("twitter")}>
          <Twitter />
        </IconButton>
        <IconButton onClick={() => onShare("facebook")}>
          <Facebook />
        </IconButton>
        <IconButton onClick={() => onShare("whatsapp")}>
          <WhatsApp />
        </IconButton>
        <IconButton onClick={() => onShare("telegram")}>
          <Telegram />
        </IconButton>
        <IconButton onClick={() => onShare("mail")}>
          <Mail />
        </IconButton>
      </div>
    </ModalDrawer>
  );
};

export default Component;
