import React from "react";
import { Card, Typography } from "@mui/material";
import { Person } from "@mui/icons-material";

import { User } from "types";

const UserHorizontalCard = (props: { user: User }) => {
  return (
    <Card className="flex cursor-pointer items-center justify-between p-2 hover:shadow-xl">
      <section className="flex items-center gap-5">
        <Person />
        <Typography variant="h6">{props.user.firstName}</Typography>
      </section>
      <section>
        <Typography className="font-bold" variant="subtitle1">
          {props.user.email}
        </Typography>
      </section>
    </Card>
  );
};

export default UserHorizontalCard;
