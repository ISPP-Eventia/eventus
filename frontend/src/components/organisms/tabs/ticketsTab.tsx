import { Typography } from "@mui/material";
import { TicketHorizontalCard } from "components/molecules";
import { Link } from "react-router-dom";
import { Participation } from "types";

export interface TicketsTabProps {
  participations: Participation[];
}

const TicketsTab = (props: TicketsTabProps) => {
  const { participations } = props;
  return (
    <section>
      <Typography variant="h4">Mis Tickets</Typography>
      {participations.length === 0 ? (
        <div>
          No tiene ningún ticket,
          <Link to="/events"> encuentra un evento!</Link>
        </div>
      ) : (
        <div className="mt-6 grid grid-cols-1 gap-4 md:grid-cols-3 xl:grid-cols-4">
          {participations.map((participation) => (
            <TicketHorizontalCard
              participation={participation}
              key={participation.id}
            />
          ))}
        </div>
      )}
    </section>
  );
};

export default TicketsTab;
