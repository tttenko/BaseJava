package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    @BeforeEach
    void setUp() {
        storage = new ListStorage();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }


    @Test
    void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Resume[] resume = storage.getAll();
        assertArrayEquals(new Resume[]{}, resume);
    }

    @Test
    void save() {
        Resume newResume = new Resume("uuid4");
        storage.save(newResume);
        assertSize(4);
        assertGet(newResume);
    }

    @Test
    void update() {
        Resume updateResume = new Resume("uuid1");
        storage.update(updateResume);
        assertSame(updateResume, storage.get("uuid1"));
    }

    @Test
    void get() {
        storage.get(UUID_1);
        storage.get(UUID_2);
        storage.get(UUID_3);
    }

    protected void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("smth"));
    }

    @Test
    void delete() {
        storage.delete("uuid3");
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid3"));
    }

    @Test
    void deleteNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("smth"));
    }

    @Test
    void getAll() {
        Resume[] allResumes = storage.getAll();
        assertSize(3);
        assertArrayEquals(new Resume[]{RESUME_1,RESUME_2,RESUME_3}, allResumes);
    }
}