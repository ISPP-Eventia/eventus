import React from "react";
import { AppRoutes } from "routes";

function App() {
  return (
    <div className="App">
      <header>{/*TODO: move this to a header component*/}</header>
      <main>
        <AppRoutes />
      </main>
      <footer>{/*TODO: move this to a footer component*/}</footer>
    </div>
  );
}

export default App;
