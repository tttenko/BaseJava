package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.Collection;
import java.util.Map;

public class DataStreamSerializer implements ChoiceSerializer{
    @Override
    public void doWrite(Resume r, OutputStream file) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(file)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            write(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

//            dos.writeInt(contacts.size());
//            for(Map.Entry<ContactType, String> entry : contacts.entrySet()) {
//                dos.writeUTF(entry.getKey().name());
//                dos.writeUTF(entry.getValue());
//            }
        }
    }

    @Override
    public Resume doRead(InputStream file) throws IOException {
        try(DataInputStream dis = new DataInputStream(file)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();

            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            return resume;

        }

    }

    private interface ElemForWrite<T> {
        void write(T t) throws IOException;
    }

    private <T> void write(DataOutputStream dos, Collection<T> collection, ElemForWrite<T> writer) throws IOException{
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}
