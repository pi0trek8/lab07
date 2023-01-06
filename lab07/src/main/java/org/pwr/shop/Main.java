package org.pwr.shop;

import org.pwr.shop.controller.Controller;

import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            controller.initialize();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
