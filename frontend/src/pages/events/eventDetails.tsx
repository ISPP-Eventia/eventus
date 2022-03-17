import React from "react";

import { DummyEvent1 } from "mocks";

import { ParticipateForm } from "components/organisms";
import Page from "../page";

const EventDetailPage = () => {
  // TODO
  return (
    <Page
      title="DETAIL PAGE TODO"
      actions={[<ParticipateForm event={DummyEvent1} />]}
    ></Page>
  );
};

export default EventDetailPage;
