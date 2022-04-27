import { Typography } from "@mui/material";

import { sponsorshipApi } from "api";
import { EventUs, Sponsorship } from "types";

import { Accept, ImageSlider } from "components/molecules";
import { ShareModal } from "components/templates";

const Component = (props: {
  sponsorship: Sponsorship;
  event: EventUs;
  callback: () => void;
}) => {
  const onAccept = (accepted: boolean) => {
    !!props.sponsorship &&
      !!props.sponsorship.id &&
      sponsorshipApi
        .acceptSponsorship(props.sponsorship.id, accepted)
        .then(() => props.callback());
  };

  return (
    <div className="relative flex h-auto w-full flex-col items-center justify-center rounded-md bg-black bg-opacity-5">
      <div className="max-h-24 w-full rounded-md object-cover">
        <ImageSlider media={props.sponsorship.media} />
      </div>
      {props.sponsorship.isAccepted === null ? (
        <Accept
          onAccept={onAccept}
          info={
            <Typography variant="body1">
              {props.sponsorship.quantity}€
            </Typography>
          }
        />
      ) : (
        <div className="absolute inset-0 z-20 flex items-end justify-between rounded-md bg-black bg-opacity-20 px-2 pb-1 opacity-0 transition-opacity duration-200 hover:opacity-100">
          <ShareModal
            type="sponsorship"
            entity={{ sponsorship: props.sponsorship, event: props.event }}
          />
          <Typography variant="h5" color="whitesmoke" className="font-bold">
            {props.sponsorship.quantity}€
          </Typography>
        </div>
      )}
    </div>
  );
};

export default Component;
