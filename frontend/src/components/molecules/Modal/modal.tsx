import { ReactNode } from "react";

import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

const Component = (props: {
  title: string;
  opener: {
    title: string;
    color?: "primary" | "secondary" | "success";
    disable?: boolean;
  };
  children?: ReactNode;
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
        disabled={props.opener.disable}
      >
        {props.opener.title}
      </Button>
      <Dialog
        open={open}
        onClose={() => setOpen(false)}
        className="hidden md:inline"
      >
        <div className="min-w-[400px]">
          <DialogTitle>{props.title}</DialogTitle>
          <DialogContent>{props.children}</DialogContent>
          <DialogActions>
            <Button variant="text" onClick={() => setOpen(false)}>
              Cerrar
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
