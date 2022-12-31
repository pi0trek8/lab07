package org.pwr.client.controllers;

import java.util.List;

public interface Controller {

    void register();

    List<Object> getProducts();

    Object getStatus(Object id);

    void placeOrder(Object product);

    void subscribe();

    void unsubscribe();
}
