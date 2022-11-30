package com.yeshtery.picturepublishing.exception.files;

public class NotFoundFile extends RuntimeException {
    public NotFoundFile() {
        super("Not Found File");
    }
}
