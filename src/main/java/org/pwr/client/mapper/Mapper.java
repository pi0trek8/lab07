package org.pwr.client.mapper;

import model.ItemType;
import model.OrderLine;

import java.util.List;

public class Mapper {
    public OrderLine mapToOrderLine(int id, String advert, int quantity, List<ItemType> items) {
        var item = items.get(id);

        OrderLine orderLine = new OrderLine(item, quantity, advert);

        return orderLine;
    }

}
