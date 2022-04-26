import { useQuery } from "react-query";
import { Carousel } from "antd";

import { mediaApi } from "api";
import { Media } from "types";

import { Loader } from "components/atoms";

const Component = (props: { media?: Media[] }) => {
  const mediaQueryId = "media " + props.media?.map((m) => m.id).join(",");
  const { isLoading, data } = useQuery(mediaQueryId, async () => {
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
            width="100"
            height="100"
          />
        ))
      )}
    </Carousel>
  );
};

export default Component;
