package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.printf("Места для резюме %s больше нет.\n", r);
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.printf("Резюме %s уже существует.\n", r);
        } else {
            addToStorage(r, index);
            size++;
            System.out.printf("Резюме %s добавлено.\n", r);
        }
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.printf("Резюме %s не обновленно\n", resume);
        }
        storage[index] = resume;
        System.out.printf("Резюме %s обновленно\n", resume);
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.printf("Резюме %s не найдено.\n", uuid);
        }
        removeFromStorage(index);
        storage[size - 1] = null;
        size--;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.printf("Резюме %s не найдено\n", uuid);
            return null;
        }
        return storage[index];
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addToStorage(Resume resume, int index);

    protected abstract void removeFromStorage(int index);
}
