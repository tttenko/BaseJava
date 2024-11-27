package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {

        return storage.containsKey(searchKey.getFullName());
    }

    @Override
    protected void doSave(Resume r, Resume searchKey) {

        storage.put(searchKey.getFullName(), r);
    }

    @Override
    protected void doUpdate(Resume r, Resume searchKey) {

        storage.put(searchKey.getFullName(), r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {

        return storage.get(searchKey.getFullName());
    }

    @Override
    protected void doDelete(Resume searchKey) {

        storage.remove(searchKey.getFullName());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Resume::getFullName)
                        .thenComparing(Resume::getUuid))
                .toList();
    }
}
