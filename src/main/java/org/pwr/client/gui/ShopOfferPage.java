package org.pwr.client.gui;

import model.ItemType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 300);
    }

    public void addProduct(List<ItemType> itemTypeList) {
        for (var item : itemTypeList) {
            tableModel.addRow(new Object[]{itemTypeList.indexOf(item), item.getName(), item.getPrice(), item.getCategory()});
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(var i = 0; i < productTable.getColumnModel().getColumnCount(); i++ ){
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

}
