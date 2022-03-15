import React from "react";
import { Box, Typography } from "@mui/material";

const Page = (props: {
  title?: string;
  children?: JSX.Element | JSX.Element[] | string;
  actions?: JSX.Element | JSX.Element[] | string;
}) => {
  return (
    <section className="block w-full py-5 px-4 md:px-8 lg:px-24 xl:px-48">
      <Box className="flex items-center justify-between md:flex-row">
        {props.title && <Typography variant="h1">{props.title}</Typography>}
        {props.actions && (
          <div className="flex flex-col gap-2 md:flex-row md:justify-end">
            {props.actions}
          </div>
        )}
      </Box>
      <Box>{props.children}</Box>
    </section>
  );
};

export default Page;
