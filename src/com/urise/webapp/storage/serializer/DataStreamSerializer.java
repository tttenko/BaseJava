package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements ChoiceSerializer {
    @Override
    public void doWrite(Resume r, OutputStream file) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(file)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();

            write(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            write(dos, r.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case QUALIFICATIONS:
                        write(dos, ((ListSection) section).getItems(), path -> dos.writeUTF(path));
                        break;
                    case OBJECTIVE:
                    case ACHIEVEMENT:
                        dos.writeUTF(((TextSection) section).getContentText());
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        write(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());

                            write(dos, org.getPositions(), position -> {
                                writeLocalDate(dos, position.getStartDate());
                                writeLocalDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    @Override
    public Resume doRead(InputStream file) throws IOException {
        try (DataInputStream dis = new DataInputStream(file)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);

            read(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            read(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case QUALIFICATIONS:
                return new ListSection(listReader(dis, () -> dis.readUTF()));
            case OBJECTIVE:
            case ACHIEVEMENT:
                return new TextSection(dis.readUTF());
            case EDUCATION:
            case EXPERIENCE:
                return new OrganizationSection(listReader(dis, () -> new Organization(
                        new Link(dis.readUTF(), dis.readUTF()),
                        listReader(dis, () -> new Organization.Position(
                                localDateRead(dis), localDateRead(dis), dis.readUTF(), dis.readUTF()
                        ))
                )));
            default:
                throw new IllegalStateException();
        }
    }

    private LocalDate localDateRead(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private <T> List<T> listReader(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();

        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface WriterForElem<T> {
        void write(T t) throws IOException;
    }

    private <T> void write(DataOutputStream dos, Collection<T> collection, WriterForElem<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private interface Process {
        void process() throws IOException;
    }

    private void read(DataInputStream dis, Process processing) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processing.process();
        }
    }
}