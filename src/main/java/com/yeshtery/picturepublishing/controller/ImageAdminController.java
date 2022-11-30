package com.yeshtery.picturepublishing.controller;

import com.yeshtery.picturepublishing.dto.ImageAdminDto;
import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.mapper.ImageAdminDtoMapper;
import com.yeshtery.picturepublishing.model.Image;
import com.yeshtery.picturepublishing.service.ImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ImageAdminController {

    private final ImageService imageService;


    public ImageAdminController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("admin/image/{status}/{id}")
    public ImageAdminDto getOne(@PathVariable Long id, @PathVariable FileStatus status) {
        return ImageAdminDtoMapper.imageToImageAdminDto(imageService.findByIdAndStatus(id, status));
    }

    @GetMapping("admin/image/{status}")
    public List<ImageAdminDto> getAll(@PathVariable FileStatus status) {

        return imageService.findAll(status)
                .stream().map(ImageAdminDtoMapper::imageToImageAdminDto).toList();
    }


    @PatchMapping("admin/image/{id}/status")
    public Image changeStatus(@PathVariable Long id, @RequestBody FileStatus status) {
        // TODO: test
        return imageService.setStatus(id, status);
    }
}
