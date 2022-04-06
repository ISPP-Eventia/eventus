import { useCallback, useEffect, useState } from "react";
import { Button, Typography } from "@mui/material";
import { Search, Star } from "@mui/icons-material";
import { useNavigate } from "react-router";
import { useQuery } from "react-query";
import { Link, useSearchParams } from "react-router-dom";

import { EventUs } from "types";
import { eventApi } from "api";

import { Loader } from "components/atoms";
import { EventCard } from "components/molecules";
import Page from "../page";
import {
  CalendarFilled,
  DollarCircleFilled,
  TrophyFilled,
} from "@ant-design/icons";

type SortBy =
  | "precio"
  | "calificación"
  | "fecha"
  | "premio"
  | "búsqueda"
  | "all";

const EventListPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const { isLoading, data: events } = useQuery("events", () =>
    eventApi.getEvents().then((response) => response?.data as EventUs[])
  );

  const onNewEventClick = () => {
    navigate("/events/new");
  };

  const [displayed, setDisplayed] = useState<EventUs[]>([]);
  const [sortBy, setSortBy] = useState<SortBy>("calificación");

  const showAll = () => {
    setSortBy("all");
    events && setDisplayed(events);
  };

  const selectByPrice = () => {
    setSortBy("precio");
    const results = events?.sort((a, b) => a.price - b.price);
    setDisplayed(results?.slice(0, 10) || []);
  };

  const selectByRating = useCallback(() => {
    setSortBy("calificación");
    const results = events
      ?.filter((event) => !!event.rating)
      .sort((a, b) => (b?.rating || 0) - (a?.rating || 0));
    setDisplayed(results?.slice(0, 10) || []);
  }, [events]);

  const selectByDate = () => {
    setSortBy("fecha");
    const results = events?.sort(
      (a, b) =>
        new Date(a?.startDate || "").getTime() -
        new Date(b?.startDate || "").getTime()
    );
    setDisplayed(results?.slice(0, 10) || []);
  };

  const selectByPrize = () => {
    setSortBy("premio");
    const results = events
      ?.filter((event) => !!event.prize)
      .sort((a, b) => a?.prize || 0 - (b.prize || 0));
    setDisplayed(results?.slice(0, 10) || []);
  };

  const selectBySearch = useCallback(
    (search: string) => {
      setSortBy("búsqueda");
      const results = events?.filter(
        (event) =>
          event?.title?.toLowerCase().includes(search.toLowerCase()) ||
          event?.description?.toLowerCase().includes(search.toLowerCase()) ||
          event?.organizer?.email
            ?.toLowerCase()
            .includes(search.toLowerCase()) ||
          event?.organizer?.firstName
            ?.toLowerCase()
            .includes(search.toLowerCase()) ||
          event?.organizer?.lastName
            ?.toLowerCase()
            .includes(search.toLowerCase())
      );
      setDisplayed(results?.slice(0, 10) || []);
    },
    [events]
  );

  const AddEvent = (
    <Button variant="contained" color="primary" onClick={onNewEventClick}>
      Nuevo Evento
    </Button>
  );

  useEffect(() => {
    events && selectByRating();
  }, [events, selectByRating]);

  useEffect(() => {
    if (searchParams.get("search")) {
      selectBySearch((searchParams.get("search") as string) || "");
    }
  }, [searchParams, selectBySearch]);

  return (
    <Page title="Eventos" actions={AddEvent}>
      {isLoading ? (
        <Loader />
      ) : (
        <>
          <div className="my-2 flex flex-wrap justify-between gap-2 md:justify-start">
            <Button
              onClick={selectByRating}
              variant={sortBy === "calificación" ? "contained" : "outlined"}
            >
              <Star />
            </Button>
            <Button
              onClick={selectByDate}
              variant={sortBy === "fecha" ? "contained" : "outlined"}
            >
              <CalendarFilled />
            </Button>
            <Button
              onClick={selectByPrize}
              variant={sortBy === "premio" ? "contained" : "outlined"}
            >
              <TrophyFilled />
            </Button>

            <Button
              onClick={selectByPrice}
              variant={sortBy === "precio" ? "contained" : "outlined"}
            >
              <DollarCircleFilled />
            </Button>
            <Button
              onClick={selectByPrice}
              variant={sortBy === "búsqueda" ? "contained" : "outlined"}
              disabled={sortBy !== "búsqueda"}
            >
              <Search />
            </Button>
            <Button onClick={showAll} variant="text">
              Mostrar todos
            </Button>
          </div>
          <Typography variant="h5" className="mt-8">
            {sortBy === "all" ? "Todos los eventos" : "Eventos por " + sortBy}
          </Typography>
          <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5">
            {displayed?.map((e) => (
              <EventCard event={e} />
            ))}
            {!displayed?.length && (
              <span>
                No hay eventos disponibles,
                <Link to="/events/new"> crea uno!</Link>
              </span>
            )}
          </section>
        </>
      )}
    </Page>
  );
};

export default EventListPage;
