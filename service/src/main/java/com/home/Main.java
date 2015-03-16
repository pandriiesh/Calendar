package com.home;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        logger.info("Service started");
        //local code review (vtegza): use (ClassPathXmlApplicationContext)context.registerShutdownHook for grace shutdown @ 16.03.15
    }
}
