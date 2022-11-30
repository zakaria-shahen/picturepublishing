package com.yeshtery.picturepublishing.controller;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FilesController {

    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files/accept/{fileName}")
    public ResponseEntity<Resource> getAcceptFiles(@PathVariable String fileName,
                                             Authentication authentication) {

        return getResourceResponseEntity(FileStatus.ACCEPT, fileName);
    }


    @GetMapping("/files/unprocessed/{fileName}")
    public ResponseEntity<Resource> getUnprocessedFiles(@PathVariable String fileName,
                                             Authentication authentication) {

        return getResourceResponseEntity(FileStatus.UNPROCESSED, fileName);
    }

    private ResponseEntity<Resource> getResourceResponseEntity(FileStatus unprocessed, String fileName) {

        Resource resource = fileService.findByFileName(unprocessed, fileName);

        String contentType = "image/" + fileName.substring(fileName.indexOf(".") + 1);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}
