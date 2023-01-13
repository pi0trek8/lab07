/**
 * @Autor Piotr Szczypior
 * @Jar mvn package in the directory of project
 * @Run-jar java -jar lab07.jar arguments
 *
 * @Argumets
 * @1 port
 * @2 server url
 */



package org.pwr.shop;

import org.pwr.shop.controller.Controller;

import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            controller.initialize(args);
        } catch (RemoteException e ) {
            throw new RuntimeException(e);
        }
    }
}
