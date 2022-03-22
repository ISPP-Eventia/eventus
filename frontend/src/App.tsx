import React from "react";
import { createTheme, responsiveFontSizes, ThemeProvider } from "@mui/material";
import { QueryClient, QueryClientProvider } from "react-query";

import { AppRoutes } from "routes";
import { AppHeader, AppFooter } from "pages";
import "antd/dist/antd.css";

import theme from "config/theme.config.json";

const queryClient = new QueryClient();

function App() {
  return (
    <div className="App">
      <ThemeProvider theme={responsiveFontSizes(createTheme(theme))}>
        <QueryClientProvider client={queryClient}>
          <AppHeader />
          <AppRoutes />
          <AppFooter />
        </QueryClientProvider>
      </ThemeProvider>
    </div>
  );
}

export default App;
