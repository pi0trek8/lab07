package org.pwr.seller.controller;

import model.Status;
import org.pwr.seller.frontend.Gui;
import org.pwr.seller.service.Service;

import java.rmi.RemoteException;

import static java.lang.Thread.sleep;

public class Controller {
    private final Gui gui;

    private final Service service;

    public Controller(Gui gui, Service service) {
        this.gui = gui;
        this.service = service;
    }

    public void initialize() {
        service.initialize();
    }

    public void downloadContent() {
        Thread thread = new Thread(() -> {
            while (true) {
                gui.getSubmittedOrdersList().clear();
                try {
                    gui.getSubmittedOrdersList().addProducts(service.getSubmittedOrders());
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
            String status = (String) gui.getStatusComboBox().getSelectedItem();

            try {
                service.editStatus(id, Status.valueOf(status));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            gui.getStatusComboBox().setSelectedIndex(0);
            gui.getOrderIdTextField().setText("");
        });

        gui.getClearButton().addActionListener(e -> {
            gui.getStatusComboBox().setSelectedIndex(0);
            gui.getOrderIdTextField().setText("");
        });
    }
}
