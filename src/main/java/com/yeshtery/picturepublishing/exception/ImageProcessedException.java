package com.yeshtery.picturepublishing.exception;

public class ImageProcessedException extends RuntimeException {
    public ImageProcessedException() {
        super("The image has been previously processed");
    }
}
