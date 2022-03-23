import { Global } from "@emotion/react";
import { styled } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { grey } from "@mui/material/colors";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
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
  opener: {
    title: string;
    color?: "primary" | "secondary" | "success";
    disable?: boolean;
  };
  children?: React.ReactNode;
  actions?: {
    title: string;
    color?: "primary" | "secondary" | "success";
    onClick: () => void;
  }[];
  open: boolean;
  setOpen: (open: boolean) => void;
}) {
  const { open, setOpen } = props;

  return (
    <>
      <Button
        variant="contained"
        color={props.opener.color}
        onClick={() => setOpen(true)}
        disabled={props.opener.disable}
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
        className="md:hidden"
      >
        <div className="absolute bottom-full w-full rounded-t-lg bg-white px-4 py-3">
          <Puller />
        </div>
        <div className="block rounded-t-lg px-4 py-2">
          {props.children}
          {props.actions &&
            props.actions.map((action, index) => (
              <Button
                key={index}
                variant="contained"
                color={action.color}
                onClick={action.onClick}
                fullWidth
              >
                {action.title}
              </Button>
            ))}
        </div>
      </SwipeableDrawer>
    </>
  );
}
