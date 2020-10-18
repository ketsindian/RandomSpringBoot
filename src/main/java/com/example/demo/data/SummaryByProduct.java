package com.example.demo.data;

public class SummaryByProduct {

    private String productName;

    private double totalAmount;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "SummaryByProduct{" +
                "productId=" + productName +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
