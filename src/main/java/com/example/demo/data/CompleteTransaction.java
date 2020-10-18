package com.example.demo.data;

public class CompleteTransaction extends Transaction{

    private String productName;

    private String productManufacturingCity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductManufacturingCity() {
        return productManufacturingCity;
    }

    public void setProductManufacturingCity(String productManufacturingCity) {
        this.productManufacturingCity = productManufacturingCity;
    }

    @Override
    public String toString() {
        return "CompleteTransaction{" +
                super.toString()+
                "productName='" + productName + '\'' +
                ", productManufacturingCity='" + productManufacturingCity + '\'' +
                '}';
    }
}
