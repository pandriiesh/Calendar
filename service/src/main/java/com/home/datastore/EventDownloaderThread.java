package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;

public class EventDownloaderThread implements Runnable {

    private final File file;
    private final Event event;

    public EventDownloaderThread(File file, Event event) {
        this.file = file;
        this.event = event;
    }

    @Override
    public void run() {
        try {
            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            EventAdapter eventAdapter = new EventAdapter(event);
            marshaller.marshal(eventAdapter, file);

        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
