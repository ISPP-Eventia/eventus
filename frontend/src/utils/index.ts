import { EventFormValues } from "types";

export function convertEventValueToRequestBody(values: EventFormValues) {
  const { title, price, description, fromTo } = values;

  const [from, to] = fromTo;

  return {
    title,
    price,
    description,
    from: from.toISOString(),
    to: to.toISOString(),
  };
}

export default {};
