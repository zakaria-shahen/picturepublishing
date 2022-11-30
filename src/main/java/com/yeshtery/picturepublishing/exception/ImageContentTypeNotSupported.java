package com.yeshtery.picturepublishing.exception;

public class ImageContentTypeNotSupported extends RuntimeException {
    public ImageContentTypeNotSupported() {
        super("Your image type not supported, we support png, jpg, and gif only");
    }
}
