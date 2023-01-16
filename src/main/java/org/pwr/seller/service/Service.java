package org.pwr.seller.service;

import interfaces.IShop;
import model.Status;
import model.SubmittedOrder;
import org.pwr.seller.config.CustomPolicy;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.List;

public class Service {
    private IShop shop;
    private static String URL = "shop";
    private static String HOST = "localhost";
    private static int PORT = 1099;

    public void initialize(String[] args) {
        if(args.length > 0 ) {
            PORT = Integer.parseInt(args[0]);
            URL = args[1];
            HOST = args[2];
        }

        Policy.setPolicy(new CustomPolicy());
        System.out.println("New policy has been set");

        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager has been set");
        }

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

    public Status findStatus(Integer id) throws RemoteException {
        var statusOptional = shop.getSubmittedOrders()
                .stream().filter(order -> order.getId() == id)
                .map(SubmittedOrder::getStatus)
                .findFirst();

        return statusOptional.orElse(Status.NEW);
    }
}
