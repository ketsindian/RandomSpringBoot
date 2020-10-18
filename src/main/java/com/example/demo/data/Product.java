package com.example.demo.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Product")
public class Product {

    @Id
    private long productId;

    private String productName;

    private String productManufacturingCity;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

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
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productManufacturingCity='" + productManufacturingCity + '\'' +
                '}';
    }
}
