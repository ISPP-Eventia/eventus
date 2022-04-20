import { useRef } from "react";
import { IconButton } from "@mui/material";
import { Instagram, Twitter, WhatsApp } from "@mui/icons-material";

import utils from "utils";
import { SocialMedia } from "types";

import { ModalDrawer } from "components/organisms";

const Component = () => {
  const closeModalRef = useRef<any>(null);

  const onShare = (socialMedia: SocialMedia) => {
    utils.share.shareEvent(socialMedia);
  };

  return (
    <ModalDrawer
      title="Compartir"
      opener={{
        title: "Compartir",
        color: "primary",
      }}
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      <div className="flex gap-5">
        <IconButton onClick={() => onShare("twitter")}>
          <Twitter />
        </IconButton>
        <IconButton onClick={() => onShare("insta")}>
          <Instagram />
        </IconButton>
        <IconButton onClick={() => onShare("whatsapp")}>
          <WhatsApp />
        </IconButton>
      </div>
    </ModalDrawer>
  );
};

export default Component;
