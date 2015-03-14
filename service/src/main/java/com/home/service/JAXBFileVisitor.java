package com.home.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class JAXBFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if(attrs.isSymbolicLink()) {
            System.out.printf("Symbolic link: %s", file);
        } else if(attrs.isRegularFile()) {
            System.out.printf("Regular file: %s", file);;
        } else {
            System.out.printf("Other: %s", file);
        }
        System.out.printf("Size: %s bytes\r\n", attrs.size());
        return FileVisitResult.CONTINUE;
    }
}
