package com.yeshtery.picturepublishing.service;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.exception.ImageProcessedException;
import com.yeshtery.picturepublishing.exception.NotFoundImage;
import com.yeshtery.picturepublishing.exception.files.NotFoundFileStatus;
import com.yeshtery.picturepublishing.model.Image;
import com.yeshtery.picturepublishing.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final FileService fileService;

    public ImageService(ImageRepository imageRepository, FileService fileService)  {
        this.imageRepository = imageRepository;
        this.fileService = fileService;
    }

    public Image findByIdAndStatus(Long id, FileStatus status) {

        return imageRepository.findByIdAndStatus(id, status)
                .orElseThrow(NotFoundImage::new);

    }

    public List<Image> findAll(FileStatus status) {
        return imageRepository.findAllByStatusIs(status);
    }

    private Image save(Image image){
        return imageRepository.save(image);
    }

    public Image save(Image image, MultipartFile file) {

        image.setFileName(fileService.saveUnprocessedFile(file));
        return save(image);
    }

    public List<Image> saveAll(Image...image){
        return imageRepository.saveAll(Arrays.stream(image).toList());
    }

    // Authority: ADMIN
    public Image setStatus(Long id, FileStatus status) {
        Image image = imageRepository.findById(id)
                .orElseThrow(NotFoundImage::new);

        if (image.getStatus() != FileStatus.UNPROCESSED) {
            throw new ImageProcessedException();
        }

        switch (status) {
            case REJECT -> {
                fileService.deleteFileFromUnprocessedFolder(image.getFileName());
                image.setStatus(FileStatus.REJECT);
                image.setFileName(null);
            }

            case ACCEPT -> {
                fileService.copyFileToAcceptFolder(image.getFileName());
                image.setStatus(FileStatus.ACCEPT);
            }

            default -> throw new NotFoundFileStatus();
        }

        return imageRepository.save(image);

    }


}
