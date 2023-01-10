package org.pwr.seller.frontend;


import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private SubmittedOrdersList submittedOrdersList;

    private JButton editButton;

    private JButton clearButton;

    private JTextField orderIdTextField;

    private JTextField statusTextField;

    private JComboBox<String> statusComboBox;


    public Gui() {
        setTitle("Seller");
        setLayout(new GridLayout(2, 1));
        setSize(new Dimension(700, 400));
        submittedOrdersList = new SubmittedOrdersList();

        var panel = formPanel();

        add(submittedOrdersList);
        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private JPanel formPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setSize(new Dimension(700, 50));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;

        JLabel productNameLabel = new JLabel("Order id:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(productNameLabel, constraints);
        orderIdTextField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(orderIdTextField, constraints);

        JLabel statusLabel = new JLabel("New status:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(statusLabel, constraints);

//        statusTextField = new JTextField(20);
//
//        panel.add(statusTextField, constraints);

        statusComboBox = new JComboBox<>(new String[] {"NEW", "PROCESSING", "READY", "DELIVERED"});
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(statusComboBox, constraints);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            orderIdTextField.setText("");
            statusTextField.setText("");
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(clearButton, constraints);

        editButton = new JButton("Confirm");
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(editButton, constraints);

        return panel;
    }

    public SubmittedOrdersList getSubmittedOrdersList() {
        return submittedOrdersList;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JTextField getOrderIdTextField() {
        return orderIdTextField;
    }

    public JTextField getStatusTextField() {
        return statusTextField;
    }

    public JComboBox<String> getStatusComboBox() {
        return statusComboBox;
    }
}
