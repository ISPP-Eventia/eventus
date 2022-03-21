import React, { ReactNode } from "react";

import { Cancel, Check } from "@mui/icons-material";
import { IconButton } from "@mui/material";

const Accept = (props: {
  onAccept: (accepted: boolean) => void;
  info: ReactNode;
}) => {
  return (
    <div className="flex w-full items-center justify-between rounded-md px-2 hover:shadow-md">
      <div>{props.info}</div>
      <div className="flex gap-1">
        <IconButton color="success" onClick={() => props.onAccept(true)}>
          <Check />
        </IconButton>
        <IconButton color="error" onClick={() => props.onAccept(false)}>
          <Cancel />
        </IconButton>
      </div>
    </div>
  );
};

export default Accept;
