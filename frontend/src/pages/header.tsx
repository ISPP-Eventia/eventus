import React from "react";

import Logo from "assets/Logo.svg";
import SearchBar from "components/molecules/SearchBar/SearchBar";

const AppHeader = () => {
  return (
    <header className="fixed top-0 z-50 flex w-full items-center justify-start gap-5 rounded-b-xl bg-brand px-4 py-3 md:px-8 lg:px-24 xl:px-48">
      <img src={Logo} alt="React Logo" className="h-6" />
      <span className="font-bold">
        <span className="text-brand-light">Event</span>
        <span className="text-brand-lighter">Us</span>
      </span>
      <SearchBar/>
    </header>
  );
};

export default AppHeader;
