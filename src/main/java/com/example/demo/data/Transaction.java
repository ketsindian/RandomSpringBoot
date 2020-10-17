package com.example.demo.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Transaction {
    private long transactionId;

    private long productId;

    private double transactionAmount;

    private String transactionDatetime;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDatetime() {
        return transactionDatetime;
    }

    public void setTransactionDatetime(String transactionDatetime) {
        this.transactionDatetime = transactionDatetime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", productId=" + productId +
                ", transactionAmount=" + transactionAmount +
                ", transactionDatetime='" + transactionDatetime + '\'' +
                '}';
    }
}
