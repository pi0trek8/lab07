package org.pwr.client.controller;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.Client;
import model.Order;
import model.OrderLine;
import model.Status;
import org.pwr.client.exception.NoSuchOrderException;
import org.pwr.client.gui.Gui;
import org.pwr.client.mapper.Mapper;
import org.pwr.client.model.PlacedOrder;
import org.pwr.client.policy.CustomPolicy;
import org.pwr.client.status.StatusListenerImpl;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    private static String URL = "shop";
    private static String HOST = "localhost";
    private static int PORT = 1099;

    private static Integer id;

    private final List<OrderLine> orders = new ArrayList<>();
    private final List<PlacedOrder> placedOrders = new ArrayList<>();

    private final IStatusListener statusListener = new StatusListenerImpl(this);

    private IShop shop;

    private Gui gui;

    private final Mapper mapper = new Mapper();

    private Client client;

    public Controller() throws RemoteException {
        String name = JOptionPane.showInputDialog(null, "Enter a name:", "Client", JOptionPane.QUESTION_MESSAGE);
        if (Objects.equals(name, "")){
            System.exit(0);
        }
        client = new Client();
        client.setName(name);
        gui = new Gui(name);
    }

    public void register(String[] args) {

        if(args.length > 0 ) {
            PORT = Integer.parseInt(args[0]);
            URL = args[1];
            HOST = args[2];
        }

        Policy.setPolicy(new CustomPolicy());

        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager has been set");
        }

        try {
            System.out.println("Registering to shop...");
            Registry registry = LocateRegistry.getRegistry(HOST,PORT);
            shop = (IShop) registry.lookup(URL);
            System.out.println("Registered!!");
            id = shop.register(client);
            System.out.println("Yours id = " + id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unexpected error has occurred!");
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
            if(orders.size() == 0) {
                System.out.println("Shopping cart is empty!");
                return;
            }

            PlacedOrder placedOrder;
            try {
                placedOrder = placeOrder();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            placedOrders.add(placedOrder);
            gui.getOrderedProductListPage().addProduct(placedOrder);

            gui.getShoppingCartPage().clearPage();
        });

        gui.getSubscribeButton().addActionListener(e -> {
            boolean isSuccess;
            try {
                isSuccess = shop.subscribe(statusListener, id);
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
                isSuccess = unsubscribe();
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
                newStatus = shop.getStatus(id);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            refreshStatus(id, newStatus);
        });

        gui.getDeleteSingleButton().addActionListener(e -> {
            if(orders.size() == 0) {
                System.out.println("The shopping cart is empty!");
                return;
            }
           int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter an id:", "Delete", JOptionPane.QUESTION_MESSAGE));

            if(orders.get(id) == null) {
                System.out.println("There is no product with id = " + id);
                return;
            }

           orders.remove(id);
           gui.getShoppingCartPage().refresh(orders);
        });

        gui.getDeleteAllButton().addActionListener(e -> {
            orders.clear();
            gui.getShoppingCartPage().clearPage();
        });
    }

    public void refreshStatus(int id, Status newStatus) {
        var order = placedOrders.stream().filter(o -> o.getId().equals(id)).findFirst();
        if (order.isEmpty()){
            throw new NoSuchOrderException("There is no order with id = " + id);
        }
        order.get().setStatus(newStatus);
        gui.getOrderedProductListPage().refresh(placedOrders);
    }

    private boolean  unsubscribe() throws RemoteException {
        return shop.unsubscribe(id);
    }

    private PlacedOrder placeOrder() throws RemoteException {
        Order order = new Order(id);

        for (var orderLine : orders) {
            order.addOrderLine(orderLine);
        }
        orders.clear();
        int orderId = shop.placeOrder(order);
        PlacedOrder placedOrder = new PlacedOrder();
        placedOrder.setId(orderId);
        placedOrder.setOrder(order);
        placedOrder.setStatus(Status.NEW);

        return placedOrder;
    }
}
