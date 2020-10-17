package com.example.demo.service;

import com.example.demo.data.Product;
import com.example.demo.data.Transaction;
import com.example.demo.utils.TransactionException;

import java.util.List;

public interface ITransactionService {

    public List<Transaction> getLatestTransactions() throws TransactionException;

    public List<Product> getStaticProductData() throws TransactionException;

}
