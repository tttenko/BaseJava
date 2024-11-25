package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractStorageTest {

    protected final Storage storage;

    private final String UUID_1 = "uuid1";
    private final String UUID_2 = "uuid2";
    private final String UUID_3 = "uuid3";

    private final Resume RESUME_1 = new Resume(UUID_1);
    private final Resume RESUME_2 = new Resume(UUID_2);
    private final Resume RESUME_3 = new Resume(UUID_3);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.clear();
        Resume RESUME = new Resume("uuid1");
        storage.save(RESUME);
        assertSize(1);
        assertGet(RESUME);
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume("uuid1");
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get("uuid1"));
    }

    @Test
    public void delete() {
        storage.delete("uuid3");
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid3"));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getFullName()));
    }

    @Test
    public void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("smth"), "Данное резюме не существует");
    }

    @Test
    void getAllSorted() {
        storage.clear();
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume3 = new Resume("uuid3");

        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);

        List<Resume> sortedStorage = storage.getAllSorted();

        List<Resume> expected = Arrays.asList(resume1, resume2, resume3);

        assertEquals(expected, sortedStorage);
    }

}
