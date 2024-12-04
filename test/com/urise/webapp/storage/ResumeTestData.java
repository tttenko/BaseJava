package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.Month;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+79953220959");
        resume.addContact(ContactType.MOBILE, "No");
        resume.addContact(ContactType.HOME_PHONE, "No");
        resume.addContact(ContactType.TELEGRAM, "@tttenko");
        resume.addContact(ContactType.MAIL, "tttenko37@gmail.com");
        resume.addContact(ContactType.LINKEDIN, "No");
        resume.addContact(ContactType.GITHUB, "tttenko");
        resume.addContact(ContactType.STATCKOVERFLOW, "No");
        resume.addContact(ContactType.HOME_PAGE, "No");

        resume.addSection(SectionType.PERSONAL, new ListSection("Коммуникабельность", "Пунктуальность", "Стрессоустойчивость" ));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Junior Java Developer"));
        resume.addSection(SectionType.ACHIEVEMENT, new TextSection("No"));

        resume.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("BaseJava", "https://topjava.ru/basejava",
                        new Organization.Position(2024, Month.AUGUST, "Junior", "BaseJava"))));

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("BaseJava, TopJava", "https://topjava.ru/basejava",
                        new Organization.Position(2024, Month.AUGUST, 2025, Month.SEPTEMBER, "Junior", "BaseJava, TopJava"))
        ));

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("TopJava, MasterJava", "https://topjava.ru/masterjava",
                        new Organization.Position(2024, Month.JANUARY, 2026, Month.NOVEMBER, "Middle", "TopJava, MasterJava"))
        ));

        return resume;

    }

}
