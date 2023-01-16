package org.pwr.client.controller;

import interfaces.IStatusListener;
import model.OrderLine;
import model.Status;
import org.pwr.client.exception.NoSuchOrderException;
import org.pwr.client.gui.Gui;
import org.pwr.client.model.PlacedOrder;
import org.pwr.client.service.ClientService;
import org.pwr.client.status.StatusListenerImpl;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.Objects;

public class Controller {




    private final IStatusListener statusListener = new StatusListenerImpl(this);


    private Gui gui;



    private ClientService service;

    public Controller() throws RemoteException {
        service = new ClientService();

        String name = JOptionPane.showInputDialog(null, "Enter a name:", "Client", JOptionPane.QUESTION_MESSAGE);
        if (Objects.equals(name, "")){
            System.exit(0);
        }
        service.createClient(name);

        gui = new Gui(name);
    }

    public void register(String[] args) {
        service.register(args);
    }

    public void downloadShopData() throws RemoteException {
        gui.getShopOfferPage().addProduct(service.getItemList());
    }

    public void createListeners() {
        gui.getConfirmButton().addActionListener(e -> {

            int productId = Integer.parseInt(gui.getProductField().getText());
            String advert = gui.getLabelField().getText();
            int quantity = Integer.parseInt(gui.getQuantityField().getText());

            OrderLine order = service.addNewOrderLine(productId, advert, quantity);
            gui.getShoppingCartPage().addProduct(order);

            gui.getProductField().setText("");
            gui.getLabelField().setText("");
            gui.getQuantityField().setText("");


        });

        gui.getClearButton().addActionListener(e -> {
            gui.getProductField().setText("");
            gui.getLabelField().setText("");
            gui.getQuantityField().setText("");
        });

        gui.getOrderButton().addActionListener(e -> {
            PlacedOrder placedOrder = service.getPlacedOrder();
            if (placedOrder == null) return;
            gui.getOrderedProductListPage().addProduct(placedOrder);

            gui.getShoppingCartPage().clearPage();
        });

        gui.getSubscribeButton().addActionListener(e -> {
            boolean isSuccess;
            try {
                isSuccess = service.subscribe(statusListener);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            if(isSuccess) {
                gui.getSubscribeButton().setEnabled(false);
                gui.getUnsubscribeButton().setEnabled(true);
                System.out.println("Subscribed to order!");
            }
        });

        gui.getUnsubscribeButton().addActionListener(e -> {
            boolean isSuccess;
            try {
                isSuccess = service.unsubscribe();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            if(isSuccess) {
                gui.getSubscribeButton().setEnabled(true);
                gui.getUnsubscribeButton().setEnabled(false);
                System.out.println("Unsubscribed to order!");

            }
        });

        gui.getRefreshButton().addActionListener(e -> {
            Status newStatus;
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter an id:", "Delete", JOptionPane.QUESTION_MESSAGE));
            try {
                newStatus = service.getStatus(id);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            refreshStatus(id, newStatus);
        });

        gui.getDeleteSingleButton().addActionListener(e -> {

           int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter an id:", "Delete", JOptionPane.QUESTION_MESSAGE));


            service.removeOrderLine(id);

           gui.getShoppingCartPage().refresh(service.getCreatedOrderLines());
        });

        gui.getDeleteAllButton().addActionListener(e -> {
            service.deleteAllOrderLines();
            gui.getShoppingCartPage().clearPage();
        });
    }



    public void refreshStatus(int id, Status newStatus) {
        service.refreshStatus(id, newStatus);
        gui.getOrderedProductListPage().refresh(service.getPlacedOrders());
    }



}
