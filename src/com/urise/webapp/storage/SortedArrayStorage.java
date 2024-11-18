package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int insertIndex = -index - 1;

        if (insertIndex < size) {
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        }

        storage[insertIndex] = resume;
    }

    @Override
    protected void fillRemovedResume(int index) {
        int numMoved = size - index - 1;
        if (index < size  - 1) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }


}
