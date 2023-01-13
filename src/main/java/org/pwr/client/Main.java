/**
 * @Autor Piotr Szczypior
 * @Jar mvn package in the directory of project
 * @Run-jar java -cp lab07.jar org.pwr.client.Main arguments
 *
 * @Argumets
 * @1 port
 * @2 server url
 * @3 host
 */
package org.pwr.client;

import org.pwr.client.controller.Controller;

import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws RemoteException {

        Controller controller = new Controller();
        controller.register(args);
        controller.downloadShopData();
        controller.createListeners();
    }
}
