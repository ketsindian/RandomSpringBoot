package com.example.demo.service;

import com.example.demo.data.Product;
import com.example.demo.data.SummaryByCity;
import com.example.demo.data.SummaryByProduct;
import com.example.demo.data.Transaction;
import com.example.demo.utils.TransactionException;

import java.util.List;

public interface ITransactionService {

    public List<Transaction> getLatestTransactionsFromFile() ;

    public List<Product> getStaticProductDataFromFile() ;

    public Transaction getTransactionById(long transactionId) ;

    public List<SummaryByProduct> getSummaryByProduct(long numberOfDays) ;

    public List<SummaryByCity> getSummaryByCity(long numberOfDays) ;
}
