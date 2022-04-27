import { Select } from "antd";
import { useEffect } from "react";
import { ITag } from "types";

export interface TagsFormProps {
  options: ITag[];
  value?: number[];
  onChange?: (medias: number[]) => void;
}

const TagsForm = (props: TagsFormProps) => {
  const { options, value, onChange } = props;

  useEffect(() => {
    if (!value) {
      if (onChange) {
        onChange([]);
      }
    }
  }, [onChange, value]);

  const handleChange = (values: number[]) => {
    if (onChange) {
      onChange(values);
    }
  };

  return (
    <Select
      mode="tags"
      style={{ width: "100%" }}
      placeholder="Tags Mode"
      onChange={handleChange}
      value={value}
    >
      {options.map((option) => (
        <Select.Option key={option.id} value={option.id}>
          {option.name}
        </Select.Option>
      ))}
    </Select>
  );
};

export default TagsForm;
