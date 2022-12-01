package com.yeshtery.picturepublishing.mapper;

import com.yeshtery.picturepublishing.dto.ImageAdminDto;
import com.yeshtery.picturepublishing.model.Image;

public class ImageAdminDtoMapper {

    private ImageAdminDtoMapper() {}

    public static ImageAdminDto imageToImageAdminDto(Image image) {
        return ImageAdminDto.builder()
                .id(image.getId())
                .category(image.getCategory())
                .description(image.getDescription())
                .imagePath("/files/" + image.getStatus().name() + "/" + image.getFileName())
                .status(image.getStatus())
                .build();

    }


}
