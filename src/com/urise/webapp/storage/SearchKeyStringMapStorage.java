package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchKeyStringMapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        String uuid = (String) searchKey;
        storage.put(uuid, r);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        String uuid = (String) searchKey;
        storage.put(uuid, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        String uuid = (String) searchKey;
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(Object searchKey) {
        String uuid = (String) searchKey;
        storage.remove(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream()
                .sorted((r1, r2) -> {
                    int temp = r1.getFullName().compareTo(r2.getFullName());
                    if (temp == 0) {
                        return r1.getUuid().compareTo(r2.getUuid());
                    }
                    return temp;

                })
                .toList();
    }
}
