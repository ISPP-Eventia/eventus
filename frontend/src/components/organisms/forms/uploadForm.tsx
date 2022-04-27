import { useEffect } from "react";
import { useQuery } from "react-query";
import { Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";

import { mediaApi } from "api";
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
  }, [onChange, value]);

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
    const mediaToUpload = { id: mediaId };
    if (onChange) {
      onChange(value ? [...value, mediaToUpload] : [mediaToUpload]);
    }
  };

  const handleOnChange = ({ file }: any) => {
    const newMedias = (value ?? []).filter(
      (m) => m.id !== file.originFileObj.mediaId
    );
    if (onChange) {
      onChange(newMedias);
    }
  };

  const mediaQueryId = "media" + (value ?? []).join(",");
  const { isLoading, data: media } = useQuery(mediaQueryId, async () => {
    if (!value || value.length === 0) {
      return [];
    }
    return Promise.all(
      value.map(async (m) => {
        const url = await mediaApi.getMedia(m.id!);

        return {
          uid: m.id.toString(),
          name: m.title ?? "Placeholder",
          url,
          status: "done" as any,
          originFileObj: {
            mediaId: m.id,
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
