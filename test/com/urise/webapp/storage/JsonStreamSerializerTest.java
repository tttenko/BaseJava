package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.JsonStreamSerializer;

public class JsonStreamSerializerTest extends AbstractStorageTest {
    public JsonStreamSerializerTest() {
        super(new PathStorage(STORAGE_DIR_2, new JsonStreamSerializer()));
    }
}
