package org.pwr.client.gui;

import org.pwr.client.models.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private JTextField productField;
    private JTextField labelField;
    private JButton confirmButton;
    private JButton clearButton;
    private ProductListPage productListPage;

    public Gui() {
        // Set the title of the frame
        super("Client view");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager for the frame
        setLayout(new BorderLayout());

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the form panel and add it to the tabbed pane
        JPanel formPanel = createFormPanel();
        tabbedPane.addTab("Form", formPanel);

        // Create the board panel and add it to the tabbed pane
        productListPage = new ProductListPage();
        tabbedPane.addTab("Board", productListPage);

        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);

        // Pack and set the frame to be visible
        pack();
        setVisible(true);
    }

    private JPanel createFormPanel() {
        // Create a panel to hold the form components
        JPanel panel = new JPanel();

        // Set the layout manager for the panel
        panel.setLayout(new GridBagLayout());

        // Create a grid bag constraints object
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;

        // Create the product name label and add it to the panel
        JLabel productNameLabel = new JLabel("Product name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(productNameLabel, constraints);

        // Create the product field and add it to the panel
        productField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(productField, constraints);

        // Create the product label label and add it to the panel
        JLabel productLabelLabel = new JLabel("Product label:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(productLabelLabel, constraints);

        // Create the label field and add it to the panel
        labelField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(labelField, constraints);

        // Create the confirm button and add it to the panel
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String product = productField.getText();
                String label = labelField.getText();

                // Add the product to the board
                addProductToBoard(product, label);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(confirmButton, constraints);

        // Create the clear button and add it to the panel
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productField.setText("");
                labelField.setText("");
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(clearButton, constraints);
        return panel;
    }

    private void addProductToBoard(String product, String label) {
        productListPage.addProduct(new Product(1,product, label, 23.3, "INACTIVE"));
    }

    public static void main(String[] args) {
        new Gui();
    }
}