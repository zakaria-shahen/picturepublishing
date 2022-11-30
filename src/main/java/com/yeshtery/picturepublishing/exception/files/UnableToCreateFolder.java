package com.yeshtery.picturepublishing.exception.files;

public class UnableToCreateFolder extends RuntimeException {
    public UnableToCreateFolder() {
        super("An error occurred while creating folder");
    }
}
