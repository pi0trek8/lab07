package org.pwr.client.gui;

import model.OrderLine;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ShoppingCartPage extends JPanel {

    private static int num = 0;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ShoppingCartPage() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nr");
        tableModel.addColumn("Product name");
        tableModel.addColumn("Cost");
        tableModel.addColumn("Category");
        tableModel.addColumn("Advert");
        tableModel.addColumn("Quantity");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 400);
    }

    public void addProduct(OrderLine orderLine) {
        tableModel.addRow(new Object[]{num, orderLine.getIt().getName(), BigDecimal.valueOf(orderLine.getCost()).setScale(2, RoundingMode.HALF_UP),
                orderLine.getIt().getCategory(), orderLine.getAdvert(), orderLine.getQuantity()});

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(var i = 0; i < productTable.getColumnModel().getColumnCount(); i++ ){
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        num++;
    }

    public void clearPage() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        num = 0;
    }

    public void refresh(List<OrderLine> orders) {
        clearPage();
        for(var orderLine : orders) {
            addProduct(orderLine);
        }
    }
}