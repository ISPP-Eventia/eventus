import React from "react";
import { Box, Typography } from "@mui/material";

const Page = (props: {
  title: string;
  children?: JSX.Element | JSX.Element[] | string;
}) => {
  return (
    <Box className="block w-full h-screen py-5 px-10">
      <Typography variant="h1">{props.title}</Typography>
      {props.children}
    </Box>
  );
};

export default Page;
