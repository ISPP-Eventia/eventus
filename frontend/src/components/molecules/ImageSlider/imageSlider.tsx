import { Carousel } from "antd";
import { Media } from "types";


const Component = (props: {
  media?: Media[];
}) => {
    
  return (
    <Carousel autoplay>
        props.media?.length === 0 ? (
            <img
            alt="img"
            className="w-full rounded-md object-cover"
            src={
                "https://via.placeholder.com/2000x1000"
            }
            />
        ) : ({props.media?.map((media) => (
            <img
            alt="img"
            className="w-full rounded-md object-cover"
            src={media?.path}
            />
            ))}
        )
    </Carousel>
  );

};

export default Component;
