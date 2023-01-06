package org.pwr.shop.impl;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import org.pwr.shop.exception.OrderNotFoundException;
import org.pwr.shop.gui.Gui;
import org.pwr.shop.mapper.Mapper;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ShopImpl extends UnicastRemoteObject implements IShop {
    Gui gui = new Gui();
    private static Integer allClients = 0;
    Map<Integer, Client> clientIdToClient = new HashMap<>();

    List<SubmittedOrder> submittedOrders = new ArrayList<>();
    Mapper mapper = new Mapper();
    private Map<Integer, IStatusListener> clientIdToStatusListener = new HashMap<>();

    public ShopImpl() throws RemoteException {
    }

    @Override
    public int register(Client c) throws RemoteException {

        if (c != null){
            clientIdToClient.put(allClients, c);
            System.out.println(allClients);
            refreshClients();

            return allClients++;
        }
        return -1;
    }

    @Override
    public List<ItemType> getItemList() throws RemoteException {
        var item1 = new ItemType();
        item1.setPrice(20);
        item1.setCategory(2);
        item1.setName("Cup");

        var item2 = new ItemType();
        item2.setCategory(1);
        item2.setName("Bottle");
        item2.setPrice(30.5f);

        var item3 = new ItemType();
        item3.setPrice(5.5f);
        item3.setName("Pen");
        item3.setCategory(1);

        return List.of(item1, item2, item3);
    }

    @Override
    public int placeOrder(Order order) throws RemoteException {
        SubmittedOrder submittedOrder = mapper.mapToSubmittedOrder(order);
        submittedOrders.add(submittedOrder);

        System.out.println("Placed order. Order id " + submittedOrder.getId());
        System.out.println("Info: " + submittedOrder.getOrder().getOll().get(0).getIt().getName());

        refreshOrders();

        return submittedOrder.getId();
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return submittedOrders;
    }

    @Override
    public boolean setStatus(int id, Status status) throws RemoteException {
        Optional<SubmittedOrder> submittedOrderOptional = submittedOrders.stream()
                .filter(order -> order.getId() == id)

                .findFirst();
        if (submittedOrderOptional.isEmpty()){
            return false;
        }
        SubmittedOrder submittedOrder = submittedOrderOptional.get();
        submittedOrder.setStatus(status);

        if (clientIdToStatusListener.containsKey(submittedOrder.getOrder().getClientID())){
            clientIdToStatusListener.get(submittedOrder.getOrder().getClientID()).statusChanged(id, status);
        }

        refreshOrders();
        return true;
    }

    @Override
    public Status getStatus(int id) throws RemoteException {

        Optional<SubmittedOrder> submittedOrderOptional = submittedOrders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
        if (submittedOrderOptional.isEmpty()){
            throw new OrderNotFoundException("No order has been found with given id = " + id);
        }

        return submittedOrderOptional.get().getStatus();
    }

    @Override
    public boolean subscribe(IStatusListener ic, int clientId) throws RemoteException {

        if (clientIdToStatusListener.containsKey(clientId)){
            return false;
        }
        clientIdToStatusListener.put(clientId, ic);
        return true;
    }

    @Override
    public boolean unsubscribe(int clientId) throws RemoteException {

        if (!clientIdToStatusListener.containsKey(clientId)){
            return false;
        }

        clientIdToStatusListener.remove(clientId);
        return false;
    }

    private void refreshOrders() {
        gui.getOrderListDisplay().clear();
        gui.getOrderListDisplay().addProducts(submittedOrders);
    }

    private void refreshClients() {
        gui.getClientsListDisplay().clear();
        gui.getClientsListDisplay().addClients(clientIdToClient);
    }
}
