import React from "react";
import { Alert, Snackbar } from "@mui/material";

import { Severity } from "./severity";

const Component = (props: { severity: Severity; message: string }) => {
  const [open, setOpen] = React.useState(true);

  return (
    <Snackbar
      open={open}
      onClose={() => setOpen(false)}
      autoHideDuration={3000}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <Alert severity={props.severity} onClose={() => setOpen(false)}>
        {props.message}
      </Alert>
    </Snackbar>
  );
};

export default Component;
