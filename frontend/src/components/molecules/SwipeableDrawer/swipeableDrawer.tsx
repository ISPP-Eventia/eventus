import * as React from "react";
import { Global } from "@emotion/react";
import { styled } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { grey } from "@mui/material/colors";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import Skeleton from "@mui/material/Skeleton";
import SwipeableDrawer from "@mui/material/SwipeableDrawer";

const drawerBleeding = 30;

const Puller = styled(Box)(({ theme }) => ({
  width: 30,
  height: 6,
  backgroundColor: theme.palette.mode === "light" ? grey[300] : grey[900],
  borderRadius: 3,
  position: "absolute",
  top: 8,
  left: "calc(50% - 15px)",
}));

export default function Component(props: {
  title: string;
  opener: { title: string; color?: "primary" | "secondary" | "success" };
  children?: React.ReactNode;
  actions?: { title: string; onClick: () => void }[];
}) {
  const [open, setOpen] = React.useState(false);

  return (
    <>
      <Button
        variant="contained"
        color={props.opener.color}
        onClick={() => setOpen(true)}
      >
        {props.opener.title}
      </Button>
      <CssBaseline />
      <Global
        styles={{
          ".MuiDrawer-root > .MuiPaper-root": {
            height: `calc(50% - ${drawerBleeding}px)`,
            overflow: "visible",
          },
        }}
      />
      <SwipeableDrawer
        anchor="bottom"
        open={open}
        onClose={() => setOpen(false)}
        onOpen={() => setOpen(true)}
        swipeAreaWidth={drawerBleeding}
        disableSwipeToOpen={false}
        ModalProps={{
          keepMounted: true,
        }}
      >
        <div className="absolute bottom-full w-full rounded-t-lg bg-white px-4 py-3">
          <Puller />
        </div>
        <div className="block rounded-t-lg px-4 py-2">
          {props.children ? (
            props.children
          ) : (
            <Skeleton variant="rectangular" height="100%" />
          )}
        </div>
      </SwipeableDrawer>
    </>
  );
}
