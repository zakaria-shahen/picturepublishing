package com.yeshtery.picturepublishing.service;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.enums.ImageCategory;
import com.yeshtery.picturepublishing.exception.ImageProcessedException;
import com.yeshtery.picturepublishing.exception.files.NotFoundFileStatus;
import com.yeshtery.picturepublishing.model.Image;
import com.yeshtery.picturepublishing.repository.ImageRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
class ImageServiceTest {

    @Mock
    private FileService fileService;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    private final String fileName = "1.jpg";

    private final Image image = Image.builder()
            .category(ImageCategory.MACHINE)
            .description("any")
            .fileName(fileName)
            .build();

    @BeforeEach
    void init() {
        given(imageRepository.save(any())).willReturn(image);
    }

    @Nested
    class saveMethodTest {

        @Mock
        private MultipartFile multipartFile;

        private Image output = null;

        @BeforeEach
         void init() {
            given(fileService.saveUnprocessedFile(multipartFile)).willReturn(fileName);
            output = imageService.save(image, multipartFile);
        }

        @Test
        void checkInvokeSaveUnprocessedAndSaveMethod() {
            then(fileService).should().saveUnprocessedFile(any());
            then(imageRepository).should().save(any());
        }

        @Test
        void checkReturnRightValue() {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(fileName, output.getFileName());
        }


    }

    @Nested
    class setStatusMethodTest {

        @Test
        void throwIfImageStatusInDbIsNotUnprocessed() {
            image.setStatus(FileStatus.ACCEPT);
            given(imageRepository.findById(any())).willReturn(Optional.of(image));

            Assertions.assertThrows(
                    ImageProcessedException.class,
                    () -> imageService.setStatus(1L, FileStatus.ACCEPT)
            );

        }

        @Test
        void throwIfFileStatusInputIsUnprocessed() {
            given(imageRepository.findById(any())).willReturn(Optional.of(image));

            Assertions.assertThrows(
                    NotFoundFileStatus.class,
                    () -> imageService.setStatus(1L, FileStatus.UNPROCESSED)
            );
        }

        @Test
        void checkBehaviorIfInputIsFileStatusReject() {
            image.setStatus(FileStatus.UNPROCESSED);
            given(imageRepository.findById(any())).willReturn(Optional.of(image));

            willDoNothing().given(fileService).deleteFileFromUnprocessedFolder(fileName);

            Image output = imageService.setStatus(1L, FileStatus.REJECT);

            then(fileService).should().deleteFileFromUnprocessedFolder(fileName);

            Assertions.assertNotNull(output);
            Assertions.assertEquals(FileStatus.REJECT, output.getStatus());
            Assertions.assertNull(output.getFileName());
        }

        @Test
        void checkBehaviorIfInputIsFileStatusAccept() {
            image.setStatus(FileStatus.UNPROCESSED);
            given(imageRepository.findById(any())).willReturn(Optional.of(image));

            willDoNothing().given(fileService).copyFileToAcceptFolder(fileName);

            Image output = imageService.setStatus(1L, FileStatus.ACCEPT);

            then(fileService).should().copyFileToAcceptFolder(fileName);

            Assertions.assertNotNull(output);
            Assertions.assertEquals(FileStatus.ACCEPT, output.getStatus());
            Assertions.assertEquals(fileName, image.getFileName());
        }
    }
}