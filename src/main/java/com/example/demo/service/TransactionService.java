package com.example.demo.service;

import com.example.demo.data.Product;
import com.example.demo.data.Transaction;
import com.example.demo.store.Store;
import com.example.demo.utils.CSVReadHelper;
import com.example.demo.utils.ResourceNotFoundException;
import com.example.demo.utils.TransactionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService implements  ITransactionService{


    @Value("${app.folderlocation.product}")
    private  String referenceFolderLocation;

    @Value("${app.folderlocation.transaction}")
    private  String transactionFolderLocation;

    @Override
    public List<Transaction> getLatestTransactionsFromFile() throws TransactionException {
        File[] files=getUnreadTransactionFiles(this.transactionFolderLocation);
        return CSVReadHelper.readTransaction(files[0].getPath());
    }

    @Override
    public List<Product> getStaticProductDataFromFile() throws TransactionException {
        File productFile=getProductFileInFolder(this.referenceFolderLocation);
        return CSVReadHelper.readProduct(productFile.getPath());
    }

    @Override
    public Transaction getTransactionById(long transactionId) throws TransactionException {
        return Store.getTransactionById(transactionId);
    }

    private File getProductFileInFolder(final String folderLocation) throws TransactionException {
        File folder=new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException ("configured folderlocation for product is not a directory "+folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length<1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file "+folderLocation);
        return folder.listFiles()[0];
    }

    private File[] getUnreadTransactionFiles(final String folderLocation) throws TransactionException {
        File folder=new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException ("configured folderlocation for product is not a directory "+folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length<1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file "+folderLocation);
        return folder.listFiles();
    }

}
