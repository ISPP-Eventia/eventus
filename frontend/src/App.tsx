import { createTheme, responsiveFontSizes, ThemeProvider } from "@mui/material";
import { BrowserRouter as Router } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "react-query";

import { AppRoutes } from "routes";
import { AppHeader, AppFooter } from "pages";
import "antd/dist/antd.css";

import theme from "config/theme.config.json";
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
});

function App() {
  return (
    <div className="App">
      <ThemeProvider theme={responsiveFontSizes(createTheme(theme))}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <AppHeader />
            <AppRoutes />
            <AppFooter />
          </Router>
        </QueryClientProvider>
      </ThemeProvider>
    </div>
  );
}

export default App;
