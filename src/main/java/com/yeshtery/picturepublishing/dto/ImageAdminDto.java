package com.yeshtery.picturepublishing.dto;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.enums.ImageCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ImageAdminDto {

    private Long id;

    @NotNull
    private ImageCategory category;

    @NotNull
    private FileStatus status;

    @NotNull @NotEmpty
    private String description;

    private String path;
}
