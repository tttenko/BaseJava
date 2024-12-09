package com.urise.webapp.storage;


import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.ChoiceSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final ChoiceSerializer choiceSerializer;

    public PathStorage(Path directory, ChoiceSerializer choiceSerializer) {
        Objects.requireNonNull(directory, "directory must not be null");

        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not directory");
        }

        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not directory");
        }
        this.directory = directory;
        this.choiceSerializer = choiceSerializer;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Not possible to create a file", path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            choiceSerializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return choiceSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }

    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return returnList();
    }

    @Override
    public int size() {
        List<Resume> listResume = returnList();
        return  listResume.size();
    }

    @Override
    public void clear() {
        try (Stream<Path> directoryPath = Files.list(directory)) {
            directoryPath.forEach(this::doDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Resume> returnList () {
        List<Resume> listResume = new ArrayList<>();
        try(Stream<Path> streamPath = Files.list(directory)) {
            streamPath.forEach(path -> listResume.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Directory read error because directory is null", null);
        }
        return listResume;
    }
}
