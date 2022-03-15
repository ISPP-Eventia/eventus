import React from "react";
import { createTheme, ThemeProvider } from "@mui/material";

import theme from "config/theme.config.json";
import { AppRoutes } from "routes";
import { AppHeader, AppFooter } from "pages";

function App() {
  return (
    <div className="App">
      <ThemeProvider theme={createTheme(theme)}>
        <AppHeader />
        <AppRoutes />
        <AppFooter />
      </ThemeProvider>
    </div>
  );
}

export default App;
