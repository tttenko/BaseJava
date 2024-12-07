package com.urise.webapp.storage;

public class StreamPathStorageTest extends AbstractStorageTest {
    public StreamPathStorageTest() {
        super(new StreamPathStorage(STORAGE_DIR_2, new ObjectStreamSerializer()));
    }
}
