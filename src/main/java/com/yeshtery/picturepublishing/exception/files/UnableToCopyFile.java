package com.yeshtery.picturepublishing.exception.files;

public class UnableToCopyFile extends RuntimeException {
    public UnableToCopyFile() {
        super("file could not be copied to folder");
    }
}
