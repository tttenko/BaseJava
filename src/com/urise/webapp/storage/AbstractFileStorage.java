package com.urise.webapp.storage;


import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<Path> {
    private final Path directory;

    public AbstractFileStorage(Path directory) {
        Objects.requireNonNull(directory, "directory must notbe null");

        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not directory");
        }

        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not directory");
        }

        this.directory = directory;
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
            doWrite(r, path);
        } catch (IOException e) {
            throw new StorageException("IO Exception", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, path);
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(path);
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
        List<Resume> listResume = new ArrayList<>();
        try (DirectoryStream<Path> list = Files.newDirectoryStream(directory)) {
            for (Path path : list) {
                if (path != null) {
                    listResume.add(doGet(path));
                }
            }
        } catch (IOException e) {
            throw new StorageException("Directory error ready because directory is null", null);
        }
        return listResume;
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory error ready because directory is null", null);
        }
        return list.length;
    }

    @Override
    public void clear() {
        try (DirectoryStream<Path> allFiles = Files.newDirectoryStream(directory)) {
            for (Path path : allFiles) {
                if (path != null) {
                    doDelete(path);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void doWrite(Resume r, Path path) throws IOException;
    protected abstract Resume doRead(Path path) throws IOException;
}
