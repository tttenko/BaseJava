package com.urise.webapp.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainFile {

    public static void main(String[] args) {
        Path path = Path.of("C:\\Users\\tttenko\\IdeaProjects\\BaseJava\\BaseJava\\src");

        printAllFiles(path);
    }

    public static void printAllFiles(Path dir) {
       try (DirectoryStream<Path> list = Files.newDirectoryStream(dir)) {
            for (Path path : list) {
                if (Files.isRegularFile(path)) {
                    System.out.println("File: " + path.getFileName());
                } else if (Files.isDirectory(path)) {
                    System.out.println("Directory: " + path.getFileName());
                    printAllFiles(path);
                }
            }
        } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}
