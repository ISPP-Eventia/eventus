import React from "react";

import Page from "./page";

import { ModalDrawer } from "components/organisms";
import { Button } from "@mui/material";

const TestPage = () => {
  return (
    <Page
      title="Test"
      actions={[
        <ModalDrawer
          title="Test"
          opener={{ title: "Test Participate", color: "success" }}
        >
          <div>TEST CONTENT</div>
        </ModalDrawer>,
        <ModalDrawer
          title="Test"
          opener={{ title: "Test Event", color: "primary" }}
        >
          <div>TEST CONTENT</div>
        </ModalDrawer>,
      ]}
    >
      <div className="overflow-hidden"></div>
    </Page>
  );
};

export default TestPage;
