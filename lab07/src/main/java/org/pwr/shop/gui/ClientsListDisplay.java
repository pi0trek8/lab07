package org.pwr.shop.gui;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ClientsListDisplay extends JPanel {

    private JTable productTable;
    private DefaultTableModel tableModel;

    public ClientsListDisplay() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Client id");
        tableModel.addColumn("Name");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 400);
        setVisible(true);
    }

    public void addProduct(int id, Client client) {
        tableModel.addRow(new Object[]{id, client.getName()});
    }

    public void addClients(Map<Integer, Client> idToClient) {
        for(Integer id : idToClient.keySet()) {
            tableModel.addRow(new Object[]{id, idToClient.get(id).getName()});
        }
    }

    public void clear() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }
}
