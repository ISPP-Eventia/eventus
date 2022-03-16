import React from "react";

import Page from "./page";

import { ParticipateForm } from "components/templates";

const TestPage = () => {
  return (
    <Page
      title="Test"
      actions={[<ParticipateForm event={{ id: 1, price: 2 }} />]}
    >
      <div className="overflow-hidden">content</div>
    </Page>
  );
};

export default TestPage;
