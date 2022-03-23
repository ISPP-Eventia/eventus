import { ReactNode } from "react";
import { Box, Typography } from "@mui/material";

const Page = (props: {
  title?: string;
  children?: ReactNode;
  actions?: ReactNode;
}) => {
  return (
    <section className="block w-full py-5 px-4 md:px-8 lg:px-24 xl:px-48">
      <Box className="flex items-center justify-between md:flex-row">
        {props.title && (
          <Typography
            variant="h2"
            className="max-w-[200px] overflow-hidden text-ellipsis md:max-w-none"
          >
            {props.title}
          </Typography>
        )}

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
