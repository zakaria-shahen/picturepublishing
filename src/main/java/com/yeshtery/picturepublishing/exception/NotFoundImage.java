package com.yeshtery.picturepublishing.exception;

public class NotFoundImage extends RuntimeException {
    public NotFoundImage() {
        super("Not Found Image");
    }
}
