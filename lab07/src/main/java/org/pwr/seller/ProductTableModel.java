package org.pwr.seller;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"Name", "Label", "Price", "Status"};
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        fireTableRowsInserted(products.size() - 1, products.size() - 1);
    }

    public void removeProduct(int index) {
        products.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getName();
            case 1:
                return product.getLabel();
            case 2:
                return product.getPrice();
            case 3:
                return product.getStatus();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                product.setName((String) aValue);
                break;
            case 1:
                product.setLabel((String) aValue);
                break;
            case 2:
                product.setPrice((Double) aValue);
                break;
            case 3:
                product.setStatus((String) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }
}