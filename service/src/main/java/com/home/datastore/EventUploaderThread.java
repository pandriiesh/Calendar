package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class EventUploaderThread implements Callable<Boolean> {

    private final Map<UUID, Event> eventStore;
    private final Path path;
    Logger logger = Logger.getLogger(EventUploaderThread.class);


    public EventUploaderThread(Path path, Map<UUID, Event> eventStore) {
        this.path = path;
        this.eventStore = eventStore;
    }

    @Override
    public Boolean call() {
        try {

            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            EventAdapter eventAdapter = (EventAdapter) unmarshaller.unmarshal(path.toFile());
            eventStore.put(eventAdapter.getId(), eventAdapter.asEvent());

            logger.info("Event " + path.toString() + " uploaded successfully");

            return Boolean.TRUE;

        } catch (JAXBException e) {
            logger.warn("Person " + path.toString() + " upload error:", e);
        }
        return Boolean.FALSE;
    }
}
