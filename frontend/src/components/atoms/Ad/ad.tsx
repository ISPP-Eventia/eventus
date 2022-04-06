import { Typography } from "@mui/material";

import { sponsorshipApi } from "api";
import { Sponsorship } from "types";

import { Accept } from "components/molecules";

const Component = (props: {
  sponsorship: Sponsorship;
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
      <img
        alt="img"
        className="max-h-24 w-full rounded-md object-cover"
        src={
          props.sponsorship.media?.[0]?.path ||
          "https://via.placeholder.com/1000"
        }
      />
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
        <div className="absolute inset-0 z-20 flex items-end justify-end rounded-md bg-black bg-opacity-20 px-2 opacity-0 transition-opacity duration-200 hover:opacity-100">
          <Typography variant="h5" color="whitesmoke" className="font-bold">
            {props.sponsorship.quantity}€
          </Typography>
        </div>
      )}
    </div>
  );
};

export default Component;
