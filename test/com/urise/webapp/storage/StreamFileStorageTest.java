package com.urise.webapp.storage;

public class StreamFileStorageTest extends AbstractStorageTest {
    public StreamFileStorageTest() {
        super(new StreamFileStorage(STORAGE_DIR_1, new ObjectStreamSerializer()));
    }
}
