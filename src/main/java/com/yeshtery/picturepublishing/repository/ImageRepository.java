package com.yeshtery.picturepublishing.repository;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByStatusIs(FileStatus status);

    Optional<Image> findByIdAndStatus(Long id, FileStatus status);
}
