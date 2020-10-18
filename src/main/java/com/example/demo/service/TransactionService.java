package com.example.demo.service;

import com.example.demo.data.*;
import com.example.demo.store.Store;
import com.example.demo.utils.CSVReadHelper;
import com.example.demo.utils.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService extends HelperService implements ITransactionService {


    @Value("${app.folderlocation.product}")
    private String referenceFolderLocation;

    @Value("${app.folderlocation.transaction}")
    private String transactionFolderLocation;

    Logger logger = LoggerFactory.getLogger(TransactionService.class);


    @Override
    public List<Transaction> getLatestTransactionsFromFile() {
        File[] files = getUnreadTransactionFiles(this.transactionFolderLocation);
        if(files.length>0)
        logger.info("got new files to sync transactions "+files);
        List<Transaction> transactions = new ArrayList<>();
        Arrays.stream(files).forEachOrdered(file -> transactions.addAll(CSVReadHelper.readTransaction(file.getPath())));
        for (File file : files) {
            Store.markFileAsProcessed(file);
        }
        if(transactions.size()>0)
        logger.info("got new transactions to sync "+transactions.size());
        return transactions;
    }

    @Override
    public List<Product> getStaticProductDataFromFile() {
        File productFile = getProductFileInFolder(this.referenceFolderLocation);
        return CSVReadHelper.readProduct(productFile.getPath());
    }

    @Override
    public Transaction getTransactionById(long transactionId) {
        return Store.getTransactionById(transactionId);
    }

    @Override
    public List<SummaryByProduct> getSummaryByProduct(long numberOfDays) {
        Collection<Transaction> transactions = Store.getAllTransactions();
        transactions=transactions.stream().filter(transaction -> transaction.getTransactionDatetime().
                after(Date.from(LocalDate.now().minusDays(numberOfDays).atStartOfDay(ZoneId.systemDefault()).toInstant()))).collect(Collectors.toList());
        HashMap<Long, Double> hmForAmountSUm = new HashMap<>();
        transactions.forEach(transaction -> {
            if (!hmForAmountSUm.containsKey(transaction.getProductId())) {
                hmForAmountSUm.put(transaction.getProductId(), (double) 0);
            }
            hmForAmountSUm.put(transaction.getProductId(), hmForAmountSUm.get(transaction.getProductId()) + transaction.getTransactionAmount());
        });
        return buildSummaryProductsFromHM(hmForAmountSUm);
    }

    @Override
    public List<SummaryByCity> getSummaryByCity(long numberOfDays) {
        List<CompleteTransaction> transactions = Store.getCompleteTransactions();
        transactions=transactions.stream().filter(transaction -> transaction.getTransactionDatetime().
                after(Date.from(LocalDate.now().minusDays(numberOfDays).atStartOfDay(ZoneId.systemDefault()).toInstant()))).collect(Collectors.toList());
        HashMap<String, Double> hmForAmountSUm = new HashMap<>();
        transactions.forEach(transaction -> {
            if (!hmForAmountSUm.containsKey(transaction.getProductManufacturingCity())) {
                hmForAmountSUm.put(transaction.getProductManufacturingCity(), (double) 0);
            }
            hmForAmountSUm.put(transaction.getProductManufacturingCity(), hmForAmountSUm.get(transaction.getProductManufacturingCity()) + transaction.getTransactionAmount());
        });
        return buildSummaryCityFromHM(hmForAmountSUm);
    }

    @Override
    public void syncTransactions() {
        List<Transaction> transactionsToSync = this.getLatestTransactionsFromFile();
        Store.addTransactions(transactionsToSync);
        logger.info("TransactionSyncJobCommand synced "+transactionsToSync.size()+" transactions");
    }

    @Override
    public void syncProducts() {
        Store.addProduct(this.getStaticProductDataFromFile());
        logger.info("ProductSyncJobCommand synced products");
    }

}
