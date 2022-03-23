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
  children?: React.ReactNode;
  actions?: {
    title: string;
    color?: "primary" | "secondary" | "success";
    onClick: () => void;
  }[];
  open: boolean;
  setOpen: (open: boolean) => void;
}) => {
  const { open, setOpen } = props;

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
        <div className="min-w-[400px]">
          <DialogTitle>{props.title}</DialogTitle>
          <DialogContent>{props.children}</DialogContent>
          <DialogActions>
            <Button variant="text" onClick={() => setOpen(false)}>
              Close
            </Button>
            {props.actions &&
              props.actions.map((action, index) => (
                <Button
                  key={index}
                  variant="contained"
                  color={action.color}
                  onClick={action.onClick}
                >
                  {action.title}
                </Button>
              ))}
          </DialogActions>
        </div>
      </Dialog>
    </>
  );
};

export default Component;
