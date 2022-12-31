package org.pwr.seller;

import model.Order;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductTableGUI extends JFrame {
    private JTable table;
    private ProductTableModel tableModel;

    public ProductTableGUI() {
        // Create the table model and table
        tableModel = new ProductTableModel();
        table = new JTable(tableModel);

        Product product = new Product("Cup", "Merry Chrismas", 43.23, "Finished");
        tableModel.addProduct(product);
        System.out.println(product);

        // Set the status column to use a combo box as the editor
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn statusColumn = columnModel.getColumn(3);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Active", "Inactive"});
        DefaultCellEditor editor = new DefaultCellEditor(comboBox);
        statusColumn.setCellEditor(editor);

        // Add the table to a scroll pane and add the scroll pane to the main window
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        // Set the size and location of the main window
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Set the title and default close operation
        setTitle("Product Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Show the GUI on the EDT
        SwingUtilities.invokeLater(() -> {
            ProductTableGUI gui = new ProductTableGUI();
            gui.setVisible(true);
        });

    }


}
