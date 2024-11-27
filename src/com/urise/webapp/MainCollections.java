package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class MainCollections {

    private static final Resume RESUME_1 = new Resume("uuid1", "Name1");
    private static final Resume RESUME_2 = new Resume("uuid2", "Name2");
    private static final Resume RESUME_3 = new Resume("uuid3", "Name3");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();

        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

        Iterator<Resume> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), "uuid1")) {
                iterator.remove();
            }
        }
        System.out.println(collection.toString());
    }
}
