package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void addToStorage(Resume resume, int index) {
        storage[size] = resume;
    }

    protected void removeFromStorage(int index) {
        storage[index] = storage[size - 1];
    }
}