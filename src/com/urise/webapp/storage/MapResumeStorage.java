package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        Resume resume = (Resume) searchKey;
        return storage.containsKey(resume.getFullName());
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        Resume resume = (Resume) searchKey;
        storage.put(resume.getFullName(), r);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        Resume resume = (Resume) searchKey;
        storage.put(resume.getFullName(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        Resume resume = (Resume) searchKey;
        return storage.get(resume.getFullName());
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume resume = (Resume) searchKey;
        storage.remove(resume.getFullName());
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
