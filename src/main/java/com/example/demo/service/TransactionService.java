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
public class TransactionService implements ITransactionService {


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

    private List<SummaryByProduct> buildSummaryProductsFromHM(HashMap<Long, Double> hmForAmountSUm){
        List<SummaryByProduct> summaryByProducts = new ArrayList<>();
        hmForAmountSUm.forEach((aLong, aDouble) -> {
            SummaryByProduct summaryByProduct = new SummaryByProduct();
            summaryByProduct.setProductName(Store.getProductNameById(aLong));
            summaryByProduct.setTotalAmount(aDouble);
            summaryByProducts.add(summaryByProduct);
        });
        return summaryByProducts;
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

    private List<SummaryByCity> buildSummaryCityFromHM(HashMap<String, Double> hmForAmountSUm){
        List<SummaryByCity> summaryByCities = new ArrayList<>();
        hmForAmountSUm.forEach((aString, aDouble) -> {
            SummaryByCity summaryByCity = new SummaryByCity();
            summaryByCity.setCityName(aString);
            summaryByCity.setTotalAmount(aDouble);
            summaryByCities.add(summaryByCity);
        });
        return summaryByCities;
    }

    private File getProductFileInFolder(final String folderLocation) {
        File folder = new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException("configured folderlocation for product is not a directory " + folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length < 1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file " + folderLocation);
        return folder.listFiles()[0];
    }

    private File[] getUnreadTransactionFiles(final String folderLocation) {
        File folder = new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException("configured folderlocation for product is not a directory " + folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length < 1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file " + folderLocation);
        return Arrays.stream(folder.listFiles()).filter(file -> !Store.isFileProcessed(file)).toArray(File[]::new);
    }

}
