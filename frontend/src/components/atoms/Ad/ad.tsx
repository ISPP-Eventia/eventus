import React from "react";
import { Typography } from "@mui/material";

import { Sponsorship } from "types";

import { Accept } from "components/molecules";

const Component = (props: {
  sponsorship: Sponsorship;
  callback: () => void;
}) => {
  const onAccept = (accepted: boolean) => {
    // TODO: call api in order to confirm sponsorship
    console.log(props.sponsorship, accepted);
    props.callback();
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
