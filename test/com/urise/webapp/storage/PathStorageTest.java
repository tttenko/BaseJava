package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;


public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR_2, new DataStreamSerializer()));
    }
}
