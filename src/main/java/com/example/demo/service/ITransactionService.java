package com.example.demo.service;

import com.example.demo.data.Product;
import com.example.demo.data.SummaryByCity;
import com.example.demo.data.SummaryByProduct;
import com.example.demo.data.Transaction;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    public List<Transaction> getLatestTransactionsFromFile();

    public Transaction getTransactionById(long transactionId);

    public List<SummaryByProduct> getSummaryByProduct(long numberOfDays);

    public List<SummaryByCity> getSummaryByCity(long numberOfDays);

    public void syncTransactions();

    public void syncProducts();

}
