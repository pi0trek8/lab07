package org.pwr.client.gui;

import model.ItemType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShopOfferPage extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ShopOfferPage() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Category");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 200));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 200);
    }

    public void addProduct(List<ItemType> itemTypeList) {
        for (var item : itemTypeList) {
            tableModel.addRow(new Object[]{itemTypeList.indexOf(item), item.getName(), item.getPrice(), item.getCategory()});
        }
    }

}
