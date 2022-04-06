import { PaymentMethod } from "types";
import { Table } from "antd";

const columns = [
  {
    title: "Ultimos 4 dígitos tarjeta",
    render: (data: any) => (
      <span>
        <b>**** **** ****</b> {data.card.last4}
      </span>
    ),
    key: "name",
  },
  {
    title: "Mes expiracion",
    render: (data: any) => <span>{data.card.exp_month}</span>,
    key: "age",
  },
  {
    title: "Año expiracion",
    render: (data: any) => <span>{data.card.exp_year}</span>,
    key: "address",
  },
];
const Component = (props: { payments: PaymentMethod }) => {
  return <Table dataSource={props?.payments?.data ?? []} columns={columns} />;
};

export default Component;
