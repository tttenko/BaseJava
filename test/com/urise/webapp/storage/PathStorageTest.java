package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR_3, new DataStreamSerializer()));
    }
}
