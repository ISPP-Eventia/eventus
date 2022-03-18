import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router";

import { DummyInfraestructure1, DummyInfraestructure2 } from "mocks";
import { Infraestructure } from "types";

import { InfraestructureCard } from "components/molecules";
import Page from "../page";

const InfraestructureListPage = () => {
  const navigate = useNavigate();

  const [infraestructures, setInfraestructures] = React.useState<Infraestructure[]>();

  React.useEffect(() => {
    let isCancelled = false;

    // TODO: call api and get events

    !isCancelled &&
    setInfraestructures([
        DummyInfraestructure1,
        DummyInfraestructure1,
        DummyInfraestructure2,
        DummyInfraestructure2,
        DummyInfraestructure1,
        DummyInfraestructure1,
        DummyInfraestructure2,
        DummyInfraestructure1,
        DummyInfraestructure1,
        DummyInfraestructure1,
      ]);

    return () => {
      isCancelled = true;
    };
  }, []);

  const onNewInfraestructureClick = () => {
    navigate("/infraestructures/new");
  };

  const AddInfraestructure = (
    <Button variant="contained" color="primary" onClick={onNewInfraestructureClick}>
      New Infraestructure
    </Button>
  );

  return (
    <Page title="Infraestructuras disponibles" actions={AddInfraestructure}>
      <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
        {infraestructures?.map((e) => (
          <InfraestructureCard event={e} />
        ))}
      </section>
    </Page>
  );
};

export default InfraestructureListPage;