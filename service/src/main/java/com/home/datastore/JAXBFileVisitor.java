package com.home.datastore;

import com.home.common.Event;
import com.home.common.Person;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JAXBFileVisitor extends SimpleFileVisitor<Path> {

    private final Map<UUID, Event> eventStore;
    private final Map<String, Person> personStore;
    private final ExecutorService executorService;
    private final AtomicInteger countOfFiles = new AtomicInteger();
    private final Logger logger = Logger.getLogger(JAXBFileVisitor.class);

    public JAXBFileVisitor(Map<UUID, Event> eventStore, Map<String, Person> personStore) {
        this.eventStore = eventStore;
        this.personStore = personStore;
        executorService = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

        if(attrs.isRegularFile()) {

            String fileName = path.getFileName().toString();
            Future future = null;;

            if (fileName.startsWith("event_") && fileName.endsWith(".xml")) {
                future = executorService.submit(new EventUploaderThread(path, eventStore));
            } else if (fileName.startsWith("person_") && fileName.endsWith(".xml")) {
                future = executorService.submit(new PersonUploaderThread(path, personStore));
            }


            if (future==null) {
                logger.warn("Invalid file found while uploading files: " + path);
                return FileVisitResult.CONTINUE;
            }

            try {
                if ((Boolean) future.get()) {
                    countOfFiles.incrementAndGet();
                } else {
                    logger.error("Error while uploading file " + path);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return FileVisitResult.CONTINUE;
    }

    public int getCountOfUploadedFiles() {
        return countOfFiles.get();
    }
}
