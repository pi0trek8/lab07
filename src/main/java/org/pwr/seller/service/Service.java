package org.pwr.seller.service;

import interfaces.IShop;
import model.Status;
import model.SubmittedOrder;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Service {
    private IShop shop;
    public final static String URL = "shop";
    public final static String HOST = "localhost";
    public final static int PORT = 1099;

    public void initialize() {
        try {
            System.out.println("Registering to shop...");
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            shop = (IShop) registry.lookup(URL);
            System.out.println("Registered!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unexpected error has occurred!");
        }
    }

    public void editStatus(Integer id, Status status) throws RemoteException {
        shop.setStatus(id, status);
    }

    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return shop.getSubmittedOrders();
    }
}
