package com.yeshtery.picturepublishing.model;

import com.yeshtery.picturepublishing.enums.ImageCategory;
import com.yeshtery.picturepublishing.enums.FileStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ImageCategory category;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private FileStatus status = FileStatus.UNPROCESSED;

    @NotNull @NotEmpty
    private String description;

    private String fileName;

}
