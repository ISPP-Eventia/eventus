import { SyntheticEvent, useEffect, useMemo } from "react";

import { useNavigate, useParams } from "react-router";
import { useQuery } from "react-query";
import { Box, Tab, Tabs } from "@mui/material";
import { Event, Info, LocationCity, Receipt } from "@mui/icons-material";

import { participationApi } from "api";
import { Participation } from "types";

import Page from "../page";
import { Loader } from "components/atoms";
import { TabPanel } from "components/molecules";

import {
  MyProfileTab,
  MyLocationsTab,
  MyEventsTab,
  TicketsTab,
} from "components/organisms";

const tabs = {
  events: 0,
  locations: 1,
  tickets: 2,
  info: 3,
};

const ProfilePage = () => {
  const userIdStr = localStorage.getItem("userId");
  const userId = userIdStr ? Number(userIdStr) : 0;

  const activeTabName =
    useParams<{ tab: "events" | "locations" | "tickets" | "info" }>().tab;
  const navigate = useNavigate();

  const activeTabIndex = useMemo(() => {
    return tabs[activeTabName || "events"];
  }, [activeTabName]);

  const handleChange = (event: SyntheticEvent, index: number) => {
    navigate(`/profile/${Object.keys(tabs)?.[index] || "events"}`);
  };

  const { isLoading: isLoadingParticipations, data: participations } = useQuery(
    "participations",
    () =>
      participationApi
        .getParticipationsByUser()
        .then((response) => response?.data as Participation[])
  );

  if (!userId) {
    navigate("/login");
  }

  useEffect(() => {
    localStorage.removeItem("eventId");
  }, []);

  return (
    <Page title="Mi Perfil">
      <Box className="mt-5" sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs
          value={activeTabIndex}
          onChange={handleChange}
          aria-label="basic tabs example"
        >
          <Tab icon={<Event />} />
          <Tab icon={<LocationCity />} />
          <Tab icon={<Receipt />} />
          <Tab icon={<Info />} />
        </Tabs>
      </Box>
      <TabPanel value={activeTabIndex} index={0}>
        <MyEventsTab />
      </TabPanel>
      <TabPanel value={activeTabIndex} index={1}>
        <MyLocationsTab />
      </TabPanel>
      <TabPanel value={activeTabIndex} index={2}>
        {isLoadingParticipations || !participations ? (
          <Loader />
        ) : (
          <TicketsTab participations={participations} />
        )}
      </TabPanel>
      <TabPanel value={activeTabIndex} index={3}>
        <MyProfileTab />
      </TabPanel>
    </Page>
  );
};

export default ProfilePage;
