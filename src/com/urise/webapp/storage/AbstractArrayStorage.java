package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void fillRemovedResume(int index);

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Массив переполнен", r.getUuid());
        }
        insertResume(r, (int) searchKey);
        size++;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage[(int) searchKey] = r;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (int) searchKey;
        fillRemovedResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> doGetAll() {
        return Arrays.stream(storage, 0, size)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Resume::getFullName))
                .toList();
    }
}
