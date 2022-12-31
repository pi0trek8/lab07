package org.pwr.client.models;

import interfaces.IStatusListener;

public class Product {

    private int id;
    private String name;
    private String label;
    private double price;
    private String status;

    public Product(int id, String name, String label, double price, String status) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
