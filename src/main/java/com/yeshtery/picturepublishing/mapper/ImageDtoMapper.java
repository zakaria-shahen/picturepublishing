package com.yeshtery.picturepublishing.mapper;

import com.yeshtery.picturepublishing.dto.ImageDto;
import com.yeshtery.picturepublishing.model.Image;

public class ImageDtoMapper {

    private ImageDtoMapper() {}

    public static Image imageDtoToImage(ImageDto imageDto){
        return Image.builder()
                .id(imageDto.getId())
                .category(imageDto.getCategory())
                .description(imageDto.getDescription())
                .build();
    }

    public static ImageDto imageToImageDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .category(image.getCategory())
                .description(image.getDescription())
                .imagePath("/files/" + image.getStatus().name() + "/" + image.getFileName())
                .build();

    }


}
