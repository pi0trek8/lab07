package org.pwr.client.gui;

import model.OrderLine;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ShoppingCartPage extends JPanel {

    private static int num = 0;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ShoppingCartPage() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nr");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 400);
    }

    public void addProduct(OrderLine orderLine) {
        tableModel.addRow(new Object[]{num, orderLine.getIt().getName(), orderLine.getIt().getPrice(), orderLine.getQuantity()});
        num++;
    }

    public void clearPage() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }
}