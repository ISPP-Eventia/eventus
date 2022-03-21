import React from "react";

const Component = (props: { lat: number; lng: number }) => {
  return (
    <iframe
      className="w-full rounded-md"
      title="map"
      src={`https://maps.google.com/maps?q=${props.lat},${props.lng}&z=16&output=embed`}
      loading="lazy"
    ></iframe>
  );
};

export default Component;
