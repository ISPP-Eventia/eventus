import React from "react";

import { DummyLocation1 } from "mocks";
import { Location } from "types";

import { LocationCard } from "components/molecules";
import Page from "../page";

const LocationListPage = () => {
  const locations: Location[] = [
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
    DummyLocation1,
  ];

  return (
    <Page title="Available Locations">
      <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
        {locations?.map((e) => (
          <LocationCard location={e} />
        ))}
      </section>
    </Page>
  );
};

export default LocationListPage;
