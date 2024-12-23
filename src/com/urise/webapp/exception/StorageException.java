package com.urise.webapp.exception;

import java.io.IOException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, IOException e) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, ClassNotFoundException e) {
        super(message);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}

