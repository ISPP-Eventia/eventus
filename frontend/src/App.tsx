import React from "react";
import { createTheme, responsiveFontSizes, ThemeProvider } from "@mui/material";

import theme from "config/theme.config.json";
import { AppRoutes } from "routes";
import { AppHeader, AppFooter } from "pages";
import 'antd/dist/antd.css'

function App() {
  return (
    <div className="App">
      <ThemeProvider theme={responsiveFontSizes(createTheme(theme))}>
        <AppHeader />
        <AppRoutes />
        <AppFooter />
      </ThemeProvider>
    </div>
  );
}

export default App;
