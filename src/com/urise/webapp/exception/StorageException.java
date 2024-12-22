package com.urise.webapp.exception;

import java.io.IOException;
import java.sql.SQLException;

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

    public StorageException(Exception e) {
        this.uuid = e.getMessage();
    }

    public String getUuid() {
        return uuid;
    }
}

