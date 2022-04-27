import { Tag } from "antd";
import { CSSProperties, useState } from "react";
import { ITag } from "types";

export interface TagsProps {
  tags: ITag[];
  deletable?: boolean;
  onDelete?: (tag: ITag) => void;
  style?: CSSProperties;
}

const Tags = (props: TagsProps) => {
  const { tags, deletable, onDelete, style } = props;

  const showMoreButton = tags.length > 5;
  const [showMore, setShowMore] = useState(tags.length <= 5);

  const showTags = showMore ? tags : tags.slice(0, 5);

  return (
    <div style={style}>
      {showTags.map((tag) => (
        <Tag
          style={{ backgroundColor: "white", marginBottom: "8px" }}
          closable={deletable}
          onClose={() => {
            if (onDelete) {
              onDelete(tag);
            }
          }}
        >
          {tag.name}
        </Tag>
      ))}
      {showMoreButton && (
        <div onClick={() => setShowMore(!showMore)}>
          {showMore ? "Ver menos" : "Ver mas..."}
        </div>
      )}
    </div>
  );
};

export default Tags;
