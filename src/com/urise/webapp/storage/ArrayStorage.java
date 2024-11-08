package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class ArrayStorage {
    private final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.printf("Места для резюме %s больше нет.\n", r);
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.printf("Резюме %s уже существует.\n", r);
        } else {
            storage[size] = r;
            size++;
            System.out.printf("Резюме %s добавлено.\n", r);
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.printf("\nРезюме %s обновленно.\n", resume);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.printf("Резюме %s не найдено.\n", uuid);
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected int getIndex(String resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume)) {
                return i;
            }
        }
        return -1;
    }
}