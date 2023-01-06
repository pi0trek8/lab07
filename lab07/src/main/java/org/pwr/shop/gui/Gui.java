package org.pwr.shop.gui;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private ClientsListDisplay clientsListDisplay;

    private OrderListDisplay orderListDisplay;

    public Gui() throws HeadlessException {
        setTitle("Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        orderListDisplay = new OrderListDisplay();
        tabbedPane.addTab("Orders", orderListDisplay);

        clientsListDisplay = new ClientsListDisplay();
        tabbedPane.addTab("Clients", clientsListDisplay);

        add(tabbedPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public ClientsListDisplay getClientsListDisplay() {
        return clientsListDisplay;
    }

    public void setClientsListDisplay(ClientsListDisplay clientsListDisplay) {
        this.clientsListDisplay = clientsListDisplay;
    }

    public OrderListDisplay getOrderListDisplay() {
        return orderListDisplay;
    }

    public void setOrderListDisplay(OrderListDisplay orderListDisplay) {
        this.orderListDisplay = orderListDisplay;
    }
}
