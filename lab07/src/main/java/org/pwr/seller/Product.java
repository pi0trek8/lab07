package org.pwr.seller;

public class Product {
    private String name;
    private String label;
    private double price;
    private String status;

    public Product(String name, String label, double price, String status) {
        this.name = name;
        this.label = label;
        this.price = price;
        this.status = status;
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}