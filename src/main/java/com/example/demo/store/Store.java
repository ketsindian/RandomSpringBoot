package com.example.demo.store;

import com.example.demo.data.CompleteTransaction;
import com.example.demo.data.Product;
import com.example.demo.data.Transaction;
import com.example.demo.utils.ResourceNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final ConcurrentHashMap<Long, Transaction> TRANSACTION_STORE = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Long, Product> PRODUCT_STORE = new ConcurrentHashMap<>();

    private static final HashSet<File> FILES_PROCESSED = new HashSet<>();

    public static void addTransactions(List<Transaction> transactions) {
        transactions.forEach(transaction -> TRANSACTION_STORE.putIfAbsent(transaction.getTransactionId(), transaction));
    }

    public static void addProduct(List<Product> products) {
        products.forEach(product -> PRODUCT_STORE.putIfAbsent(product.getProductId(), product));
    }

    public static Transaction getTransactionById(long transactionId) {
        Transaction transaction = TRANSACTION_STORE.getOrDefault(transactionId, new Transaction());
        if (transaction.equals(new Transaction()))
            throw new ResourceNotFoundException("transaction with id " + transactionId + " not found !");
        return transaction;
    }

    public static Collection<Transaction> getAllTransactions() {
        return TRANSACTION_STORE.values();
    }

    public static List<CompleteTransaction> getCompleteTransactions() {
        List<CompleteTransaction> completeTransactions = new ArrayList<>();
        TRANSACTION_STORE.forEach((aLong, transaction) -> {
            CompleteTransaction completeTransaction = new CompleteTransaction();
            completeTransaction.setProductId(aLong);
            completeTransaction.setTransactionId(transaction.getTransactionId());
            completeTransaction.setTransactionAmount(transaction.getTransactionAmount());
            completeTransaction.setTransactionDatetime(transaction.getTransactionDatetime());
            completeTransaction.setProductManufacturingCity(PRODUCT_STORE.get(transaction.getProductId()).getProductManufacturingCity());
            completeTransaction.setProductName(PRODUCT_STORE.get(transaction.getProductId()).getProductName());
            completeTransactions.add(completeTransaction);
        });
        return completeTransactions;
    }

    public static String getProductNameById(long productId) {
        return PRODUCT_STORE.get(productId).getProductName();
    }

    public static boolean isFileProcessed(File file) {
        return FILES_PROCESSED.contains(file);
    }

    public static boolean markFileAsProcessed(File file) {
        return FILES_PROCESSED.add(file);
    }
}
