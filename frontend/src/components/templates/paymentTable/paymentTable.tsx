 
import React from "react";
import { GridColDef } from "@mui/x-data-grid";

import { PaymentMethod } from "types";
import { Table } from "components/molecules";

const columns: GridColDef[] = [
  {
    field: "data.card.last4",
    headerName: "Ultimos 4 dígitos tarjeta",
    minWidth: 140,
  },
  {
    field: "data.card.exp_month",
    headerName: "Mes expiracion",
    minWidth: 170,
  },
  {
    field: "data.card.exp_year",
    headerName: "Año expiracion",
    minWidth: 170,
  },
];

const Component = (props: {
  payments: PaymentMethod[];
}) => {
  return (
    <Table
      rows={props.payments}
      columns={columns}
    />
  );
};

export default Component;
