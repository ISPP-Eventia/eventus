import { Card, Typography } from "@mui/material";
import {
  LocalActivityOutlined,
  EventOutlined,
  TitleOutlined,
  EuroOutlined,
} from "@mui/icons-material";

import { Participation } from "types";
import utils from "utils";
import { participationApi } from "api";

export interface ParticipationHorizontalCardProps {
  participation: Participation;
}
const ParticipationHorizontalCard = (
  props: ParticipationHorizontalCardProps
) => {
  const { participation } = props;
  return (
    <Card
      className="w-auto cursor-pointer p-2 hover:shadow-xl"
      onClick={() => participationApi.getTicket(participation.id!)}
    >
      <div className="flex items-center justify-start gap-4">
        <TitleOutlined />
        <Typography variant="h6">{participation.event.title}</Typography>
      </div>
      <div className="flex items-center justify-start gap-4">
        <EventOutlined />
        <Typography variant="h6">
          {utils.formatters.formatDateHour(participation.event.startDate || "")}
        </Typography>
      </div>
      <div className="flex items-center justify-start gap-4">
        <EuroOutlined />
        <Typography variant="h6">{participation.price}</Typography>
      </div>
      <div className="flex items-center justify-start gap-4">
        <LocalActivityOutlined />
        <Typography variant="h6">{participation.ticket}</Typography>
      </div>
    </Card>
  );
};

export default ParticipationHorizontalCard;
