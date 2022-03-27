import { useMemo } from "react";

import { useNavigate, useParams } from "react-router";
import { Box, Tab, Tabs } from "@mui/material";
import { Event, Info, LocationCity, Receipt } from "@mui/icons-material";

import { TabPanel } from "components/molecules";
import { ProfileInfoTab } from "components/organisms";
import Page from "../page";

const tabs = {
  events: 0,
  locations: 1,
  tickets: 2,
  info: 3,
};

const ProfilePage = () => {
  const activeTabName =
    useParams<{ tab: "events" | "locations" | "tickets" | "info" }>().tab;
  const navigate = useNavigate();

  const activeTabIndex = useMemo(() => {
    return tabs[activeTabName || "events"];
  }, [activeTabName]);

  const handleChange = (event: React.SyntheticEvent, index: number) => {
    navigate(`/profile/${Object.keys(tabs)[index]}`);
  };

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
        TODO: Mis Eventos
      </TabPanel>
      <TabPanel value={activeTabIndex} index={1}>
        TODO: Mis Ubicaciones
      </TabPanel>
      <TabPanel value={activeTabIndex} index={2}>
        TODO: Mis Tickets
      </TabPanel>
      <TabPanel value={activeTabIndex} index={3}>
        TODO: Mis Datos
        <ProfileInfoTab />
      </TabPanel>
    </Page>
  );
};

export default ProfilePage;
