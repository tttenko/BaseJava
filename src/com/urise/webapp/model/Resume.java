package com.urise.webapp.model;

import java.util.UUID;

public class Resume {
    private String fullName;
    private String uuid;

    public Resume(String fullName) {
        this.fullName = fullName;
        this.uuid = UUID.randomUUID().toString();

    }

    public Resume() {
        this("");
    }

//    public Resume(String fullName, String uuid) {
//        this.fullName = fullName;
//        this.uuid = uuid;
//    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

}