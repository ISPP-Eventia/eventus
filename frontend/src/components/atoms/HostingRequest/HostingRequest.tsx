import React from "react";
import { Typography } from "@mui/material";

import { Hosting } from "types";

import { Accept } from "components/molecules";
import { hostingApi } from "api";
import utils from "utils";

const HostingRequest = (props: { hosting: Hosting; callback: () => void }) => {
  const onAccept = (accepted: boolean) => {
    !!props.hosting &&
      !!props.hosting.id &&
      hostingApi
        .acceptHosting(props.hosting.id, accepted)
        .then(() => props.callback());
  };

  return (
    <div className="flex h-auto w-full flex-col items-center justify-center rounded-md bg-black bg-opacity-5">
      <div className="w-full px-5 py-2">
        <Typography variant="h5">{props.hosting.event?.title}</Typography>
        <div>
          <Typography variant="body1">
            {utils.formatters.formatDateHour(props.hosting.event!.startDate!)}
          </Typography>
          <Typography variant="body1">
            {utils.formatters.formatDateHour(props.hosting.event!.endDate!)}
          </Typography>
        </div>
      </div>
      {props.hosting.isAccepted === null && (
        <Accept
          onAccept={onAccept}
          info={
            <Typography variant="body1">{props.hosting.price} €</Typography>
          }
        />
      )}
    </div>
  );
};

export default HostingRequest;
