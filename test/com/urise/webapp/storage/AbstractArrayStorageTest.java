package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

    protected Storage storage;


    private final Resume resume1 = new Resume("uuid1");
    private final Resume resume2 = new Resume("uuid2");
    private final Resume resume3 = new Resume("uuid3");

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size(), "Размер массива должен быть 3");
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size(), "Массив должен быть пустой");
    }

    @Test
    public void save() {
        storage.clear();
        Resume resume = new Resume("uuid1");
        storage.save(resume);
        assertEquals(1, storage.size());
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume("uuid1");
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get("uuid1"));
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        assertEquals(2, storage.size(), "Массив должен уменьшиться на один");
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get("uuid1"));
    }

    @Test
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        assertArrayEquals(new Resume[]{resume1, resume2, resume3}, allResumes);
    }


    @Test
    public void testArrayOverflow() {
        try {
            // Заполняем хранилище до максимума
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail("Ошибка при заполнении массива");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("new uuid")), "Резюме переполненно");
    }

    @Test
    public void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("smth"), "Данное резюме не существует");
    }
}
