package org.pwr.client.controller;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import org.pwr.client.mapper.Mapper;
import org.pwr.client.exception.NoSuchOrderException;
import org.pwr.client.model.PlacedOrder;
import org.pwr.client.status.StatusListenerImpl;
import org.pwr.client.gui.Gui;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    public final static String URL = "shop";
    public final static String HOST = "localhost";
    public final static int PORT = 1099;

    private static Integer id;

    List<OrderLine> orders = new ArrayList<>();
    List<PlacedOrder> placedOrders = new ArrayList<>();

    IStatusListener statusListener = new StatusListenerImpl(this);

    private IShop shop;

    private Order order;

    private Gui gui;

    Mapper mapper = new Mapper();

    private Client client;

    IStatusListener iStatusListener = new IStatusListener() {
        @Override
        public void statusChanged(int id, Status status) throws RemoteException {
            refreshStatus(id, status);
        }
    };

    public Controller() throws RemoteException {
        String name = JOptionPane.showInputDialog(null, "Enter a name:", "Client", JOptionPane.QUESTION_MESSAGE);
        if (Objects.equals(name, "")){
            System.exit(0);
        }
        client = new Client();
        client.setName(name);
        gui = new Gui(name);
    }

    public void register() {
        try {
            System.out.println("Registrating to shop...");
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            shop = (IShop) registry.lookup(URL);
            System.out.println("Registered!!");
            id = shop.register(client);
            System.out.println("New Client with id = " + id + " has been registered.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unexpected error has occured!");
        }
    }

    public void downloadShopData() throws RemoteException {
        gui.getShopOfferPage().addProduct(shop.getItemList());
    }

    public void createListeners() {
        gui.getConfirmButton().addActionListener(e -> {

            int productId = Integer.parseInt(gui.getProductField().getText());
            String advert = gui.getLabelField().getText();
            int quantity = Integer.parseInt(gui.getQuantityField().getText());

            try {
                OrderLine order = mapper.mapToOrderLine(productId, advert, quantity, shop.getItemList());
                orders.add(order);

                gui.getShoppingCartPage().addProduct(order);

                gui.getProductField().setText("");
                gui.getLabelField().setText("");
                gui.getQuantityField().setText("");

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        gui.getClearButton().addActionListener(e -> {
            gui.getProductField().setText("");
            gui.getLabelField().setText("");
            gui.getQuantityField().setText("");
        });

        gui.getOrderButton().addActionListener(e -> {
            PlacedOrder placedOrder;
            try {
                int id = placeOrder();
                placedOrder = new PlacedOrder();
                placedOrder.setId(id);
                placedOrder.setStatus(Status.NEW);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            placedOrders.add(placedOrder);
            gui.getOrderedProductListPage().addProduct(placedOrder);

            gui.getShoppingCartPage().clearPage();
        });

        gui.getSubscribeButton().addActionListener(e -> {
            try {
                shop.subscribe(statusListener, id);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        gui.getUnsubscribeButton().addActionListener(e -> {
            try {
                unsubscribe();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        gui.getRefreshButton().addActionListener(e -> {
            Status newStatus;
            int id = Integer.parseInt(gui.getOrderIdTextField().getText());
            try {
                newStatus = shop.getStatus(id);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            refreshStatus(id, newStatus);
        });
    }

    public void refreshStatus(int id, Status newStatus) {
        var order = placedOrders.stream().filter(o -> o.getId().equals(id)).findFirst();
        if (order.isEmpty()){
            throw new NoSuchOrderException("There is no order with id = " + id);
        }
        order.get().setStatus(newStatus);
        refresh();
    }

    private void refresh() {
        gui.getOrderedProductListPage().refresh(placedOrders);
    }

    private void unsubscribe() throws RemoteException {
        shop.unsubscribe(id);
    }

    private Integer placeOrder() throws RemoteException {
        Order order = new Order(id);

        for (var orderLine : orders) {
            order.addOrderLine(orderLine);
        }
        orders.clear();

        return shop.placeOrder(order);
    }
}
