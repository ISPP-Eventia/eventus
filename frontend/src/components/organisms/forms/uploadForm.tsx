import { UploadOutlined } from "@ant-design/icons";
import { Upload, Progress, Button } from "antd";
import { mediaApi } from "api";
import { useEffect, useMemo } from "react";
import { useQuery } from "react-query";
import { Media } from "types";

export interface UploadFormProps {
  value?: Media[];
  onChange?: (medias: Media[]) => void;
}
export const UploadForm = (props: UploadFormProps) => {
  const { value, onChange } = props;

  useEffect(() => {
    if (!value) {
      if (onChange) {
        onChange([]);
      }
    }
  }, [value]);
  const uploadImage = async (options: any) => {
    const { onSuccess, onError, file, onProgress } = options;

    const res = await mediaApi.uploadMedia({
      file,
      onProgress,
      onSuccess,
      onError,
    });

    const mediaId = res.data?.id;
    file.mediaId = mediaId;
    const media = { id: mediaId };
    if (onChange) {
      onChange(value ? [...value, media] : [media]);
    }
  };

  const handleOnChange = ({ file }: any) => {
    const newMedias = (value ?? []).filter(
      (media) => media.id !== file.originFileObj.mediaId
    );
    if (onChange) {
      onChange(newMedias);
    }
  };

  const mediaQueryId = "media" + (value ?? []).join(",");
  const { isLoading, data: media } = useQuery(mediaQueryId, async () => {
    if (!value || value.length === 0) return [];
    return await Promise.all(
      value.map(async (media) => {
        const url = await mediaApi.getMedia(media.id!);

        return {
          uid: media.id.toString(),
          name: media.title ?? "Placeholder",
          url,
          status: "done" as any,
          originFileObj: {
            mediaId: media.id,
          } as any,
        };
      })
    );
  });

  return (
    <Upload
      accept="image/*"
      customRequest={uploadImage}
      onChange={handleOnChange}
      listType="picture-card"
      className="image-upload-grid"
      fileList={isLoading ? undefined : media}
    >
      <UploadOutlined />
    </Upload>
  );
};
