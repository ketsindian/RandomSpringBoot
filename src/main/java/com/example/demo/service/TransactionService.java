package com.example.demo.service;

import com.example.demo.data.*;
import com.example.demo.store.Store;
import com.example.demo.utils.CSVReadHelper;
import com.example.demo.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class TransactionService implements  ITransactionService{


    @Value("${app.folderlocation.product}")
    private  String referenceFolderLocation;

    @Value("${app.folderlocation.transaction}")
    private  String transactionFolderLocation;

    @Override
    public List<Transaction> getLatestTransactionsFromFile(){
        File[] files=getUnreadTransactionFiles(this.transactionFolderLocation);
        List<Transaction> transactions=new ArrayList<>();
        Arrays.stream(files).forEachOrdered(file-> transactions.addAll(CSVReadHelper.readTransaction(file.getPath())));
        for (File file : files) {
            Store.markFileAsProcessed(file);
        }
        return transactions;
    }

    @Override
    public List<Product> getStaticProductDataFromFile(){
        File productFile=getProductFileInFolder(this.referenceFolderLocation);
        return CSVReadHelper.readProduct(productFile.getPath());
    }

    @Override
    public Transaction getTransactionById(long transactionId){
        return Store.getTransactionById(transactionId);
    }

    @Override
    public List<SummaryByProduct> getSummaryByProduct(long numberOfDays) {
        List<SummaryByProduct> summaryByProducts = new ArrayList<>();
        Collection<Transaction> transactions = Store.getAllTransactions();
        HashMap<Long, Double> hmForAmountSUm = new HashMap<>();
        transactions.forEach(transaction -> {
            if (!hmForAmountSUm.containsKey(transaction.getProductId())) {
                hmForAmountSUm.put(transaction.getProductId(), (double) 0);
            }
            hmForAmountSUm.put(transaction.getProductId(), hmForAmountSUm.get(transaction.getProductId()) + transaction.getTransactionAmount());
        });
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
        List<SummaryByCity> summaryByCities = new ArrayList<>();
        List<CompleteTransaction> transactions = Store.getCompleteTransactions();
        HashMap<String, Double> hmForAmountSUm = new HashMap<>();
        transactions.forEach(transaction -> {
            if (!hmForAmountSUm.containsKey(transaction.getProductManufacturingCity())) {
                hmForAmountSUm.put(transaction.getProductManufacturingCity(), (double) 0);
            }
            hmForAmountSUm.put(transaction.getProductManufacturingCity(), hmForAmountSUm.get(transaction.getProductManufacturingCity()) + transaction.getTransactionAmount());
        });
        hmForAmountSUm.forEach((aString, aDouble) -> {
            SummaryByCity summaryByCity = new SummaryByCity();
            summaryByCity.setCityName(aString);
            summaryByCity.setTotalAmount(aDouble);
            summaryByCities.add(summaryByCity);
        });
        return summaryByCities;
    }

    private File getProductFileInFolder(final String folderLocation)  {
        File folder=new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException ("configured folderlocation for product is not a directory "+folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length<1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file "+folderLocation);
        return folder.listFiles()[0];
    }

    private File[] getUnreadTransactionFiles(final String folderLocation) {
        File folder=new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException ("configured folderlocation for product is not a directory "+folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length<1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file "+folderLocation);
        return Arrays.stream(folder.listFiles()).filter(file->!Store.isFileProcessed(file)).toArray(File[]::new);
    }

}
