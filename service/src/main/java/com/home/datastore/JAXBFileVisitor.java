package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventAdapter;
import com.home.common.Person;
import com.home.common.PersonAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JAXBFileVisitor extends SimpleFileVisitor<Path> {

    private final Map<UUID, Event> eventStore;
    private final Map<String, Person> personStore;
    private final ExecutorService executorService;

    public JAXBFileVisitor(Map<UUID, Event> eventStore, Map<String, Person> personStore) {
        this.eventStore = eventStore;
        this.personStore = personStore;
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

        if(attrs.isRegularFile()) {
            String fileName = path.getFileName().toString();

            if (fileName.startsWith("event_") && fileName.endsWith(".xml")) {
                executorService.submit(new EventUploaderThread(path, eventStore));
//                new Thread(new EventUploaderThread(path, eventStore)).start();
            } else if (fileName.startsWith("person_") && fileName.endsWith(".xml")) {
                executorService.submit(new PersonUploaderThread(path, personStore));
//                new Thread(new PersonUploaderThread(path, personStore)).start();
            }
        }

        return FileVisitResult.CONTINUE;
    }
}
