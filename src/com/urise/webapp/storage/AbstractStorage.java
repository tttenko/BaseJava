package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);
    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Resume r, Object searchKey);
    protected abstract void doUpdate(Resume r, Object searchKey);
    protected abstract Resume doGet(Object searchKey);
    protected abstract void doDelete(Object searchKey);

    private Object findExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);

        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        return searchKey;
    }

    private Object findNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);

        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }

        return searchKey;
    }

    @Override
    public void save(Resume r) {
        Object searchKey = findNotExistingSearchKey(r.getFullName());
        doSave(r, searchKey);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = findExistingSearchKey(r.getFullName());
        doUpdate(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = findExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = findExistingSearchKey(uuid);
        doDelete(searchKey);
    }
}
