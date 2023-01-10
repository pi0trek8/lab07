package org.pwr.client;

import org.pwr.client.controller.Controller;

import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws RemoteException {

        Controller controller = new Controller();
        controller.register();
        controller.downloadShopData();
        controller.createListeners();
    }
}
