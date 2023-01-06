package org.pwr.seller.controller;

import interfaces.IShop;
import model.Status;
import org.pwr.seller.frontend.Gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.Thread.sleep;

public class Controller {
    private IShop shop;
    public final static String URL = "shop";
    public final static String HOST = "localhost";
    public final static int PORT = 1099;

    Gui gui = new Gui();

    public void initialize() {
        try {
            System.out.println("Registrating to shop...");
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            shop = (IShop) registry.lookup(URL);
            System.out.println("Registered!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unexpected error has occured!");
        }
    }

    public void downloadContent() {
        Thread thread = new Thread(() -> {
            while (true) {
                gui.getSubmittedOrdersList().clear();
                try {
                    gui.getSubmittedOrdersList().addProducts(shop.getSubmittedOrders());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        thread.start();
    }

    public void createListeners() {
        gui.getEditButton().addActionListener(e -> {
            Integer id = Integer.parseInt(gui.getOrderIdTextField().getText());
            String status = gui.getStatusTextField().getText();

            try {
                editStatus(id, Status.valueOf(status));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            gui.getStatusTextField().setText("");
            gui.getOrderIdTextField().setText("");
        });

        gui.getClearButton().addActionListener(e -> {
            gui.getStatusTextField().setText("");
            gui.getOrderIdTextField().setText("");
        });
    }

    private void editStatus(Integer id, Status status) throws RemoteException {
        shop.setStatus(id, status);
    }
}
