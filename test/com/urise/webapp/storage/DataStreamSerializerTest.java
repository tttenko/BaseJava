package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class DataStreamSerializerTest extends AbstractStorageTest{
    public DataStreamSerializerTest() {
        super(new PathStorage(STORAGE_DIR_2, new DataStreamSerializer()));
    }
}
