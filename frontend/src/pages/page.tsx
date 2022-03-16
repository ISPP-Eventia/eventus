import React from "react";
import { Box, Typography } from "@mui/material";

const Page = (props: {
  title?: string;
  children?: JSX.Element | JSX.Element[] | string;
}) => {
  return (
    <section className="block w-full h-96 py-5 px-4 md:px-8 lg:px-24 xl:px-48 ">
      {props.title && <Typography variant="h1">{props.title}</Typography>}
      <Box>{props.children}</Box>
    </section>
  );
};

export default Page;
