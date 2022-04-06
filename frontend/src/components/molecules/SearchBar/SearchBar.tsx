import { Form } from "antd";
import Search from "antd/lib/input/Search";
import { useNavigate } from "react-router";

export default function SearchBar() {
  const navigate = useNavigate();

  const handleSearch = (value: string) => {
    navigate(`/events?search=${value}`);
  };

  return (
    <Form layout="inline">
      <Form.Item>
        <Search
          placeholder="Busca eventos ..."
          onSearch={handleSearch}
          style={{ minWidth: 30 }}
        />
      </Form.Item>
    </Form>
  );
}
