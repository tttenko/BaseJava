package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractStorageTest {

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");

        R1.addContact(ContactType.PHONE, "+79953220959");
        R1.addContact(ContactType.MOBILE, "No");
        R1.addContact(ContactType.HOME_PHONE, "No");
        R1.addContact(ContactType.TELEGRAM, "@tttenko");
        R1.addContact(ContactType.MAIL, "tttenko37@gmail.com");
        R1.addContact(ContactType.LINKEDIN, "No");
        R1.addContact(ContactType.GITHUB, "tttenko");
        R1.addContact(ContactType.STATCKOVERFLOW, "No");
        R1.addContact(ContactType.HOME_PAGE, "No");

        R1.addSection(SectionType.PERSONAL, new ListSection("Коммуникабельность", "Пунктуальность", "Стрессоустойчивость" ));
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Junior Java Developer"));
        R1.addSection(SectionType.ACHIEVEMENT, new TextSection("No"));

        R1.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("BaseJava", "https://topjava.ru/basejava",
                        new Organization.Position(2024, Month.AUGUST, "Junior", "BaseJava"))));

        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("BaseJava, TopJava", "https://topjava.ru/basejava",
                        new Organization.Position(2024, Month.AUGUST, 2025, Month.SEPTEMBER, "Junior", "BaseJava, TopJava"))
        ));

        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("TopJava, MasterJava", "https://topjava.ru/masterjava",
                        new Organization.Position(2024, Month.JANUARY, 2026, Month.NOVEMBER, "Middle", "TopJava, MasterJava"))
        ));
    }

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume("uuid1", "New Name");
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get("uuid1"));
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid3"));
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void getNotExist() {
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
