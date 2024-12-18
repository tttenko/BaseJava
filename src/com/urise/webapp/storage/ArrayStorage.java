package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void fillRemovedResume(int index) {
        storage[index] = storage[size - 1];
    }


}