package org.pwr.shop.gui;

import model.OrderLine;
import model.SubmittedOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderListDisplay extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;

    public OrderListDisplay() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Client id");
        tableModel.addColumn("Price");
        tableModel.addColumn("Status");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 400);
        setVisible(true);
    }

    public void addProduct(SubmittedOrder orderLine) {
        tableModel.addRow(new Object[]{orderLine.getId(), orderLine.getOrder().getClientID(), orderLine.getStatus()});
    }

    public void addProducts(List<SubmittedOrder> submittedOrders) {
        for (SubmittedOrder submittedOrder : submittedOrders) {
            tableModel.addRow(new Object[]{submittedOrder.getId(),
                    submittedOrder.getOrder().getClientID(),
                    BigDecimal.valueOf(submittedOrder.getOrder().getCost()).setScale(2, RoundingMode.HALF_UP),
                    submittedOrder.getStatus()});
        }
    }

    public void clear() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }
}