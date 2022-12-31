package org.pwr.client.gui;

import org.pwr.client.models.Product;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ProductListPage extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductListPage() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Label");
        tableModel.addColumn("Price");
        tableModel.addColumn("Status");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 400);

    }

    public void addProduct(Product product) {
        tableModel.addRow(new Object[] { product.getId(), product.getName(), product.getLabel(), product.getPrice(), product.getStatus() });
    }
}