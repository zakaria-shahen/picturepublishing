package com.yeshtery.picturepublishing.service;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.exception.ImageContentTypeNotSupported;
import com.yeshtery.picturepublishing.exception.files.NotFoundFile;
import com.yeshtery.picturepublishing.exception.files.UnableToCopyFile;
import com.yeshtery.picturepublishing.exception.files.UnableToCreateFolder;
import com.yeshtery.picturepublishing.exception.files.UnableToDeleteFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageFileService implements FileService {

    private final Path unprocessedImageFolderLocation;
    private final Path acceptImageFolderLocation;

    public ImageFileService(@Value("${file.image.dir}") String dir) {
        dir = StringUtils.cleanPath(dir);
        this.unprocessedImageFolderLocation = createUploadFolders(FileStatus.UNPROCESSED, dir);
        this.acceptImageFolderLocation = createUploadFolders(FileStatus.ACCEPT, dir);

    }

    public Resource findByFileName(FileStatus status, String fileName) {
        Path path = switch (status) {
            case UNPROCESSED -> unprocessedImageFolderLocation;
            case ACCEPT -> acceptImageFolderLocation;
            default -> throw new NotFoundFile();
        };

        return findByPath(path.resolve(fileName));
    }

    @Override
    public String saveUnprocessedFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + getContentType(file);
        Path path = unprocessedImageFolderLocation.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UnableToCopyFile();
        }

        return fileName;
    }

    @Override
    public void copyFileToAcceptFolder(String fileName) {
        Path target = acceptImageFolderLocation.resolve(fileName);
        Path source = unprocessedImageFolderLocation.resolve(fileName);

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            deleteFileFromUnprocessedFolder(fileName);
        } catch (IOException e) {
            throw new UnableToCopyFile();
        }
    }


    @Override
    public void deleteFileFromUnprocessedFolder(String fileName) {
        fileName = StringUtils.getFilename(fileName);
        var path = unprocessedImageFolderLocation.resolve(fileName);

        try {
            Files.deleteIfExists(path);

        } catch (IOException e) {
            throw new UnableToDeleteFile();
        }

    }


    private static Resource findByPath(Path path) {
        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new NotFoundFile();

        } catch (MalformedURLException e) {
            throw new NotFoundFile();
        }
    }

    private static Path createUploadFolders(FileStatus status, String dir) {
        var path = Paths.get(dir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(path);
            Files.createDirectories(path.resolve(status.name()));

        } catch (IOException e) {
            throw new UnableToCreateFolder();
        }

        return path.resolve(status.name());
    }

    private static String getContentType(MultipartFile file) {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            default -> throw new ImageContentTypeNotSupported();
        };
    }
}
