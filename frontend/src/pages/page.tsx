import { ReactNode } from "react";
import { Box, Typography } from "@mui/material";

const Page = (props: {
  title?: string;
  children?: ReactNode;
  actions?: ReactNode;
}) => {
  return (
    <section className="block w-full animate-fade-in py-5 px-4 md:px-8 lg:px-24 xl:px-48">
      <Box className="flex flex-col justify-between gap-y-2 md:flex-row md:items-center">
        {props.title && (
          <Typography variant="h3" className="overflow-hidden text-ellipsis">
            {props.title}
          </Typography>
        )}

        {props.actions && (
          <div className="flex items-center gap-2 md:justify-end">
            {props.actions}
          </div>
        )}
      </Box>
      <Box>{props.children}</Box>
    </section>
  );
};

export default Page;
