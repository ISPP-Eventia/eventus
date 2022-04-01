import ParticipationHorizontalCard from "components/molecules/Cards/participationCard";
import { Participation } from "types";

export interface TicketsTabProps {
  participations: Participation[];
}

const TicketsTab = (props: TicketsTabProps) => {
  const { participations } = props;
  return (
    <div className="flex gap-3 flex-wrap">
      {participations.length === 0 ? (
        <div>No tiene ningún ticket aún</div>
      ) : (
        participations.map((participation) => (
          <ParticipationHorizontalCard participation={participation} key={participation.id} />
        ))
      )}
    </div>
  );
};

export default TicketsTab;
