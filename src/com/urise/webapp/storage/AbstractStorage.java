package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    abstract public int size();

    @Override
    abstract public void clear();

    @Override
    abstract public void save(Resume r);

    @Override
    abstract public void update(Resume r);

    @Override
    abstract public Resume get(String uuid);

    @Override
    abstract public void delete(String uuid);

    @Override
    abstract public Resume[] getAll();
}
