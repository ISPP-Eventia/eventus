 
import React from "react";
import { GridColDef } from "@mui/x-data-grid";

import { EventUs } from "types";
import { Table } from "components/molecules";

const columns: GridColDef[] = [
  {
    field: "username",
    headerName: "Username",
    minWidth: 140,
  },
  {
    field: "first_name",
    headerName: "First name",
    minWidth: 170,
  },
  {
    field: "last_name",
    headerName: "Last name",
    minWidth: 170,
  },
  {
    field: "email",
    headerName: "Email",
    minWidth: 230,
  },
];

const Component = (props: {
  users: EventUs[];
}) => {
  return (
    <Table
      rows={props.users}
      columns={columns}
    />
  );
};

export default Component;
