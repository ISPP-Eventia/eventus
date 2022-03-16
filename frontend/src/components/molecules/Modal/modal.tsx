import React from "react";

import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

const Component = (props: {
  title: string;
  opener: { title: string; color?: "primary" | "secondary" | "success" };
  children: React.ReactNode;
  actions?: { title: string; onClick: () => void }[];
}) => {
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
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{props.title}</DialogTitle>
        <DialogContent>{props.children}</DialogContent>
        <DialogActions>
          <Button variant="text" onClick={() => setOpen(false)}>
            Close
          </Button>
          {props.actions?.map((action) => (
            <Button variant="contained" onClick={action.onClick}>
              {action.title}
            </Button>
          ))}
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Component;
