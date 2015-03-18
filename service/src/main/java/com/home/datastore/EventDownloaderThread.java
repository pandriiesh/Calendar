package com.home.datastore;

import com.home.common.Event;
import com.home.common.EventAdapter;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.util.concurrent.Callable;

public class EventDownloaderThread implements Callable<Boolean> {

    private final File file;
    private final Event event;
    private final Logger logger = Logger.getLogger(EventDownloaderThread.class);

    public EventDownloaderThread(File file, Event event) {
        this.file = file;
        this.event = event;
    }

    @Override
    public Boolean call() {
        try {
            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            EventAdapter eventAdapter = new EventAdapter(event);
            marshaller.marshal(eventAdapter, file);

            logger.info("Event " + file.getAbsolutePath() + " downloaded");

            return Boolean.TRUE;

        } catch (PropertyException e) {
            logger.warn("Event " + file.getAbsolutePath() + " downloading error: PropertyException", e);
        } catch (JAXBException e) {
            logger.warn("Event " + file.getAbsolutePath() + " downloading error: JAXBException", e);
        }
        return Boolean.FALSE;
    }
}
