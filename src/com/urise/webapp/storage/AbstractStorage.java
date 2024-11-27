package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    public final Comparator<Resume> comparatorForResume = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    protected abstract SK getSearchKey(String uuid);
    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(Resume r, SK searchKey);
    protected abstract void doUpdate(Resume r, SK searchKey);
    protected abstract Resume doGet(SK searchKey);
    protected abstract void doDelete(SK searchKey);
    protected abstract List<Resume> doGetAll();

    private SK findExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);

        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        return searchKey;
    }

    private SK findNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);

        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }

        return searchKey;
    }

    @Override
    public void save(Resume r) {
        SK searchKey = findNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public void update(Resume r) {
        SK searchKey = findExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = findExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = findExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        list.sort(comparatorForResume);
        return list;
    }
}
