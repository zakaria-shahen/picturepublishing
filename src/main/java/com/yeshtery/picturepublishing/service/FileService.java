package com.yeshtery.picturepublishing.service;

import com.yeshtery.picturepublishing.enums.FileStatus;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Resource findByFileName(FileStatus status, String name);

    String saveUnprocessedFile(MultipartFile file);

    void copyFileToAcceptFolder(String fileName);

    void deleteFileFromUnprocessedFolder(String fileName);

}
