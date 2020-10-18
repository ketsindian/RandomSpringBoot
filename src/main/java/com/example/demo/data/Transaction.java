package com.example.demo.data;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private long transactionId;

    private long productId;

    private double transactionAmount;

    private Date transactionDatetime;

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

    public Date getTransactionDatetime() {
        return transactionDatetime;
    }

    public void setTransactionDatetime(Date transactionDatetime) {
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
