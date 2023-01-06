package org.pwr.client.gui;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private JTextField productField;
    private JTextField labelField;
    private JTextField quantityField;
    private JButton confirmButton;
    private JButton clearButton;

    private JButton orderButton;

    private ShoppingCartPage shoppingCartPage;

    private ShopOfferPage shopOfferPage;

    private OrderedProductListPage orderedProductListPage;

    private JButton subscribeButton;

    private JButton unsubscribeButton;

    private JButton refreshButton;

    private JTextField orderIdTextField;

    public Gui(String name) {
        setTitle(name + " view");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel formPanel = createFormPanel();
        tabbedPane.addTab("Form", formPanel);

        JPanel shoppingCartJPanel = createShoppingCartPanel();
        tabbedPane.addTab("Shopping Card", shoppingCartJPanel);

        JPanel ordersCartPanel = createOrderPanel();

        tabbedPane.add("Your's orders", ordersCartPanel);

        add(tabbedPane, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private JPanel createOrderPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        orderedProductListPage = new OrderedProductListPage();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;

        subscribeButton = new JButton("subscribe");
        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonPanel.add(subscribeButton, constraints);

        unsubscribeButton = new JButton("unsubscribe");
        constraints.gridx = 1;
        constraints.gridy = 0;
        buttonPanel.add(unsubscribeButton, constraints);

        JLabel text = new JLabel("Order id:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        buttonPanel.add(text, constraints);

        orderIdTextField = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 1;
        buttonPanel.add(orderIdTextField, constraints);

        refreshButton = new JButton("refresh");
        constraints.gridx = 2;
        constraints.gridy = 1;
        buttonPanel.add(refreshButton, constraints);

        mainPanel.add(orderedProductListPage);
        mainPanel.add(buttonPanel);

        return mainPanel;
    }


    private JPanel createFormPanel() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
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
        JLabel productNameLabel = new JLabel("Product id:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(productNameLabel, constraints);

        // Create the product field and add it to the panel
        productField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(productField, constraints);

        JLabel productLabelLabel = new JLabel("Product label:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(productLabelLabel, constraints);

        // Create the label field and add it to the panel
        labelField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(labelField, constraints);

        JLabel quantityLabel = new JLabel("Product quantity:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(quantityLabel, constraints);

        quantityField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(quantityField, constraints);

        // Create the confirm button and add it to the panel
        clearButton = new JButton("clear");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(clearButton, constraints);

        confirmButton = new JButton("Confirm");

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(confirmButton, constraints);

        shopOfferPage = new ShopOfferPage();

        mainPanel.add(shopOfferPage);
        mainPanel.add(panel);

        return mainPanel;
    }

    public JPanel createShoppingCartPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        orderButton = new JButton("order");

        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonPanel.add(orderButton, constraints);
        buttonPanel.setPreferredSize(new Dimension(700, 50));

        shoppingCartPage = new ShoppingCartPage();
        panel.add(shoppingCartPage);
        panel.add(buttonPanel);

        return panel;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JTextField getProductField() {
        return productField;
    }

    public JTextField getLabelField() {
        return labelField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JButton getOrderButton() {
        return orderButton;
    }

    public OrderedProductListPage getOrderedProductListPage() {
        return orderedProductListPage;
    }

    public ShoppingCartPage getShoppingCartPage() {
        return shoppingCartPage;
    }

    public ShopOfferPage getShopOfferPage() {
        return shopOfferPage;
    }

    public JButton getSubscribeButton() {
        return subscribeButton;
    }

    public JButton getUnsubscribeButton() {
        return unsubscribeButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTextField getOrderIdTextField() {
        return orderIdTextField;
    }
}