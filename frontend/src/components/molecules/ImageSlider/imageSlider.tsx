import { Carousel } from "antd";
import { mediaApi } from "api";
import { Loader } from "components/atoms";
import { useQuery } from "react-query";
import { Media } from "types";

const Component = (props: { media?: Media[] }) => {
  const { isLoading, data } = useQuery("media", async () => {
    if (!props.media) return [];
    return await Promise.all(
      props.media.map(async (media) => {
        const url = await mediaApi.getMedia(media.id!);

        return {
          id: media.id,
          title: media.title,
          url,
        };
      })
    );
  });

  return (
    <Carousel autoplay>
      {(isLoading || !data) && <Loader />}
      {!data || data.length === 0 ? (
        <img
          alt="img"
          className="w-full rounded-md object-cover"
          src={"https://via.placeholder.com/2000x1000"}
        />
      ) : (
        data.map((media) => (
          <img
            key={media.id}
            alt={media.title}
            className="w-full rounded-md object-cover"
            src={media.url}
          />
        ))
      )}
    </Carousel>
  );
};

export default Component;
