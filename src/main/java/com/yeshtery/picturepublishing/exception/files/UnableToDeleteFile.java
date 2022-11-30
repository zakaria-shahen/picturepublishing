package com.yeshtery.picturepublishing.exception.files;

public class UnableToDeleteFile extends RuntimeException {
    public UnableToDeleteFile() {
        super("The file could not be deleted");
    }
}
