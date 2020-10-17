package com.example.demo.store;

import com.example.demo.data.Product;
import com.example.demo.data.Transaction;
import com.example.demo.utils.ResourceNotFoundException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static ConcurrentHashMap<Long, Transaction> TRANSACTION_STORE=new ConcurrentHashMap<>();

    private static ConcurrentHashMap<Long, Product> PRODUCT_STORE=new ConcurrentHashMap<>();

    public static void addTransactions(List<Transaction> transactions){
        transactions.forEach(transaction -> TRANSACTION_STORE.putIfAbsent(transaction.getTransactionId(),transaction));
    }

    public static void addProduct(List<Product> products){
        products.forEach(product -> PRODUCT_STORE.putIfAbsent(product.getProductId(),product));
    }

    public static Transaction getTransactionById(long transactionId){
        Transaction transaction =TRANSACTION_STORE.getOrDefault(transactionId,new Transaction());
        if(transaction.equals(new Transaction()))
            throw new ResourceNotFoundException("transaction with id "+transactionId+" not found !");
        return transaction;
    }
}
