package com.yeshtery.picturepublishing.controller;

import com.yeshtery.picturepublishing.dto.ImageDto;
import com.yeshtery.picturepublishing.enums.Authority;
import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.mapper.ImageDtoMapper;
import com.yeshtery.picturepublishing.model.Image;
import com.yeshtery.picturepublishing.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("image/{id}")
    public ImageDto getOne(@PathVariable Long id) {

        return ImageDtoMapper.imageToImageDto(imageService.findByIdAndStatus(id, FileStatus.ACCEPT));
    }


    @GetMapping("image")
    public List<ImageDto> getAll() {

        return imageService.findAll(FileStatus.ACCEPT)
                .stream().map(ImageDtoMapper::imageToImageDto).toList();
    }

    // Authority: User
    @PostMapping("image")
    public ImageDto post(@RequestPart(name = "metadata") @Valid ImageDto imageDto,
                         @RequestPart(name = "file") @NotNull @NotBlank MultipartFile file) {

        Image image = ImageDtoMapper.imageDtoToImage(imageDto);
        image.setStatus(FileStatus.UNPROCESSED);
        image.setId(null);

        image = imageService.save(image, file);

        return ImageDtoMapper.imageToImageDto(image);
    }


}
