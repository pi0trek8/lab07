package org.pwr.client.gui;

import org.pwr.client.model.PlacedOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderedProductListPage extends JPanel {

    private JTable productTable;
    private DefaultTableModel tableModel;

    public OrderedProductListPage() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Order id");
        tableModel.addColumn("Total cost");
        tableModel.addColumn("Status");

        productTable = new JTable(tableModel);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
        productTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane);

        setSize(700, 400);
    }

    public void addProduct(PlacedOrder placedOrder) {
        tableModel.addRow(new Object[]{placedOrder.getId(),
                BigDecimal.valueOf(placedOrder.getOrder().getCost()).setScale(2, RoundingMode.HALF_UP),
                placedOrder.getStatus()});
        centerCells();
    }

    public void refresh(List<PlacedOrder> placedOrders) {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }

        for(var placedOrder : placedOrders) {
            tableModel.addRow(new Object[]{placedOrder.getId(),
                    BigDecimal.valueOf(placedOrder.getOrder().getCost()).setScale(2, RoundingMode.HALF_UP),
                    placedOrder.getStatus()});
        }
        centerCells();
    }

    private void centerCells() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(var i = 0; i < productTable.getColumnModel().getColumnCount(); i++ ){
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

}
