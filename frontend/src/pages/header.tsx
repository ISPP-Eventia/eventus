import { useNavigate } from "react-router";
import { Person, PowerSettingsNew } from "@mui/icons-material";

import Logo from "assets/Logo.svg";
import SearchBar from "components/molecules/SearchBar/SearchBar";

const AppHeader = () => {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("eventId");
    localStorage.removeItem("isAdmin");
    navigate("/login");
  };

  return (
    <header className="fixed top-0 z-50 flex w-full items-center justify-between gap-5 rounded-b-xl bg-brand px-4 py-3 md:px-8 lg:px-24 xl:px-48">
      <a href="/landing" className="flex items-center justify-start gap-5">
        <img src={Logo} alt="React Logo" className="h-6" />
        <span className="hidden font-bold md:block">
          <span className="text-brand-light">Event</span>
          <span className="text-brand-lighter">Us</span>
        </span>
      </a>
      <SearchBar />
      <div className="flex gap-3">
        <Person
          className="cursor-pointer text-white"
          onClick={() => navigate("/profile")}
        />
        <PowerSettingsNew
          className="cursor-pointer text-white"
          onClick={logout}
        />
      </div>
    </header>
  );
};

export default AppHeader;
