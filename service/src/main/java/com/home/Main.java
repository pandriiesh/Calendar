package com.home;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;


public class Main {

    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws RemoteException {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        context.registerShutdownHook();

        logger.info("Service started");
    }
}
