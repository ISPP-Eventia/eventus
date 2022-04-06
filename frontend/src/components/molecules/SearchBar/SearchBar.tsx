import { useNavigate } from "react-router";
import { Form } from "antd";
import Search from "antd/lib/input/Search";

export default function SearchBar() {
  const navigate = useNavigate();

  const handleSearch = (value: string) => {
    navigate(`/events?search=${value}`);
  };

  return (
    <Form layout="inline">
      <Form.Item>
        <Search
          style={{ minWidth: 30 }}
          placeholder="Busca eventos ..."
          onSearch={handleSearch}
        />
      </Form.Item>
    </Form>
  );
}
