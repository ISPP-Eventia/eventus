import React from "react";
import { Card, Typography } from "@mui/material";
import { Person } from "@mui/icons-material";

import { User } from "types";

const UserHorizontalCard = (props: { user: User }) => {
  return (
    <Card className="flex cursor-pointer items-center justify-between p-2 hover:shadow-xl">
      <section className="flex max-w-[65%] items-center gap-2">
        <Person />
        <Typography
          variant="h6"
          className="overflow-hidden text-ellipsis whitespace-nowrap"
        >
          {props.user.firstName} {props.user.lastName}
        </Typography>
      </section>
      <section className="max-w-[35%]">
        <Typography
          className="overflow-hidden text-ellipsis whitespace-nowrap font-bold"
          variant="subtitle1"
        >
          {props.user.email}
        </Typography>
      </section>
    </Card>
  );
};

export default UserHorizontalCard;
