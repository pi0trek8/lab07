package org.pwr.shop.mapper;

import model.Order;
import model.SubmittedOrder;

public class Mapper {

    public SubmittedOrder mapToSubmittedOrder(Order order) {
        SubmittedOrder submittedOrder = new SubmittedOrder(order);

        return submittedOrder;
    }


}
