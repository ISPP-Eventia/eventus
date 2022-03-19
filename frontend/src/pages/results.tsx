import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router";
import SearchBar from "components/molecules/SearchBar/SearchBar";


import { DummyEvent1, DummyEvent2 } from "mocks";
import { EventUs } from "types";

import { EventCard } from "components/molecules";
import Page from "./page";

const ResultsPage = () => {
  const navigate = useNavigate();

  const [events, setEvents] = React.useState<EventUs[]>();

  React.useEffect(() => {
    let isCancelled = false;

    // TODO: call api and get events

    !isCancelled &&
      setEvents([
        DummyEvent1,
        DummyEvent1,
        DummyEvent2,
        DummyEvent2,
        DummyEvent1,
        DummyEvent1,
        DummyEvent2,
        DummyEvent1,
        DummyEvent1,
        DummyEvent1,
      ]);

    return () => {
      isCancelled = true;
    };
  }, []);

  return (
    <Page title="" actions={"Se han encontrado 10 eventos"}>
      <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
        {events?.map((e) => (
          <EventCard event={e} />
        ))}
      </section>
    </Page>
  );
};

export default ResultsPage;
