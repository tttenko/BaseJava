package com.urise.webapp.storage;

import static com.urise.webapp.storage.AbstractStorageTest.STORAGE_DIR;

public class ObjectStreamStorageTest extends AbstractStorageTest{
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
