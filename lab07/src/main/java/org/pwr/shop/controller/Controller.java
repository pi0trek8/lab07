package org.pwr.shop.controller;

import interfaces.IShop;
import org.pwr.shop.impl.ShopImpl;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller {
    IShop shop;
    private static Integer PORT = 1099;
    private static String URL = "shop";

    public void initialize() throws RemoteException {
        shop = new ShopImpl();
        System.out.println("Launching server...");
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind(URL, shop);
            System.out.println("Server successfully started!");
        } catch (Exception e) {
            System.out.println("Server crashed!");
            e.printStackTrace();
        }
    }
}
