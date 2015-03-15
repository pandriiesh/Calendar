package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class EventUploaderThread implements Runnable {

    private final Map<UUID, Event> eventStore;
    private final Path path;

    public EventUploaderThread(Path path, Map<UUID, Event> eventStore) {
        this.path = path;
        this.eventStore = eventStore;
    }

    @Override
    public void run() {
        try {

            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            EventAdapter eventAdapter = (EventAdapter) unmarshaller.unmarshal(path.toFile());
            eventStore.put(eventAdapter.getId(), eventAdapter.asEvent());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
