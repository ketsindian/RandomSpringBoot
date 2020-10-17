package com.example.demo.service;

import com.example.demo.data.Product;
import com.example.demo.data.Transaction;
import com.example.demo.utils.CSVReadHelper;
import com.example.demo.utils.TransactionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService implements  ITransactionService{


    @Value("${app.folderlocation.product}")
    private  String referenceFolderLocation;

    @Value("${app.folderlocation.transaction}")
    private  String transactionFolderLocation;

    @Override
    public List<Transaction> getLatestTransactions() throws TransactionException {
        File[] files=getUnreadTransactionFiles(this.transactionFolderLocation);
        return CSVReadHelper.readTransaction(files[0].getPath());
    }

    @Override
    public List<Product> getStaticProductData() throws TransactionException {
        File productFile=getProductFileInFolder(this.referenceFolderLocation);
        return CSVReadHelper.readProduct(productFile.getPath());
    }

    private File getProductFileInFolder(final String folderLocation) throws TransactionException {
        File folder=new File(folderLocation);
        if (folder.isFile())
            throw new TransactionException(404,"FILE_NOT_FOUND","configured folderlocation for product is not a directory -"+folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length<1)
            throw new TransactionException(404,"FILE_NOT_FOUND","configured folderlocation for product does not contain any file"+folderLocation);
        return folder.listFiles()[0];
    }

    private File[] getUnreadTransactionFiles(final String folderLocation) throws TransactionException {
        File folder=new File(folderLocation);
        if (folder.isFile())
            throw new TransactionException(404,"FILE_NOT_FOUND","configured folderlocation for product is not a directory"+folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length<1)
            throw new TransactionException(404,"FILE_NOT_FOUND","configured folderlocation for product does not contain any file"+folderLocation);
        return folder.listFiles();
    }
}
