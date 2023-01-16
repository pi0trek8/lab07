package org.pwr.client.service;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import org.pwr.client.exception.NoSuchOrderException;
import org.pwr.client.mapper.Mapper;
import org.pwr.client.model.PlacedOrder;
import org.pwr.client.policy.CustomPolicy;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static String URL = "IShop";
    private static String HOST = "192.168.220.15";
    private static int PORT = 1099;

    private IShop shop;
    private Integer id;
    private Client client;

    private final List<PlacedOrder> placedOrders = new ArrayList<>();

    private final List<ItemType> shopOfferList = new ArrayList<>();

    private final List<OrderLine> orders = new ArrayList<>();

    private final Mapper mapper = new Mapper();

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

    public void createClient(String name) {
        client = new Client();
        client.setName(name);
    }

    public List<ItemType> getItemList() throws RemoteException {
        var shopOffer = shop.getItemList();
        shopOfferList.addAll(shopOffer);
        return shopOfferList;
    }

    public PlacedOrder getPlacedOrder() {

            if(orders.size() == 0) {
                System.out.println("Shopping cart is empty!");
                return null;
            }

            PlacedOrder placedOrder;
            try {
                placedOrder = placeOrder();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            placedOrders.add(placedOrder);
            return placedOrder;
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

    public boolean  unsubscribe() throws RemoteException {
        return shop.unsubscribe(id);
    }

    public boolean subscribe(IStatusListener statusListener) throws RemoteException {
        return shop.subscribe(statusListener, id);
    }

    public OrderLine addNewOrderLine(int productId, String advert, int quantity) {

        OrderLine order = mapper.mapToOrderLine(productId, advert, quantity, shopOfferList);
        orders.add(order);
        return order;
    }

    public Status getStatus(int id) throws RemoteException {
        return shop.getStatus(id);
    }

    public void removeOrderLine(int id) {
        if(orders.size() == 0) {
            System.out.println("The shopping cart is empty!");
            return;
        }

        if(orders.stream().filter(o -> orders.indexOf(o) == id).findFirst().isEmpty()) {
            System.out.println("There is no product with id = " + id);
            return;
        }
        orders.remove(id);
    }

    public List<OrderLine> getCreatedOrderLines() {
        return orders;
    }

    public void deleteAllOrderLines() {
        orders.clear();
    }

    public List<PlacedOrder> getPlacedOrders() {
        return placedOrders;
    }
    public void refreshStatus(int id, Status newStatus) {
        var order = placedOrders.stream().filter(o -> o.getId().equals(id)).findFirst();
        if (order.isEmpty()){
            throw new NoSuchOrderException("There is no order with id = " + id);
        }
        order.get().setStatus(newStatus);
    }
}
