
import * as React from "react";

import { DataGrid, GridColDef, GridSelectionModel } from "@mui/x-data-grid";

const Component = (props: {
  rows: any[];
  columns: GridColDef[];
}) => {

  return (
    <div className="w-full">
      <DataGrid
        autoHeight
        rows={props.rows}
        columns={props.columns}
        pageSize={2}
      />
    </div>
  );
};

export default Component;
