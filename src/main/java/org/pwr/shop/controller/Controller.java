package org.pwr.shop.controller;

import interfaces.IShop;
import org.pwr.shop.impl.ShopImpl;
import org.pwr.shop.policy.CustomPolicy;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;

public class Controller {
    IShop shop;
    private static Integer PORT = 1099;
    private static String URL = "shop";

    public void initialize() throws RemoteException {
        Policy.setPolicy(new CustomPolicy());

        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager has been set");
        }

        shop = new ShopImpl();
        System.out.println("Launching shop...");
        try {
//            Registry registry = LocateRegistry.createRegistry(PORT);
//            registry.bind(URL, shop);
            Naming.bind(URL, shop);
            System.out.println("Shop successfully started!");
        } catch (Exception e) {
            System.out.println("Server crashed!");
            e.printStackTrace();
        }
    }
}
