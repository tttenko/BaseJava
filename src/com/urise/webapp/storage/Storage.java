package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {
    int size();

    void clear();

    void save(Resume r);

    void update(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();
}
