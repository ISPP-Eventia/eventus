import React from "react";
import { Typography } from "@mui/material";

import { Sponsorship } from "types";

import { Accept } from "components/molecules";
import { sponsorshipApi } from "api";

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
    <div className="flex h-auto w-full flex-col items-center justify-center rounded-md bg-black bg-opacity-5">
      <img
        alt="img"
        className="max-h-24 w-full rounded-md object-cover"
        src={props.sponsorship.media?.[0]?.path}
      />
      {!props.sponsorship.isAccepted && (
        <Accept
          onAccept={onAccept}
          info={
            <Typography variant="body1">
              {props.sponsorship.quantity}€
            </Typography>
          }
        />
      )}
    </div>
  );
};

export default Component;
