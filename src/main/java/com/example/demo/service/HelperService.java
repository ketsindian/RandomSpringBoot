package com.example.demo.service;

import com.example.demo.data.SummaryByCity;
import com.example.demo.data.SummaryByProduct;
import com.example.demo.store.Store;
import com.example.demo.utils.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class HelperService {

    protected List<SummaryByProduct> buildSummaryProductsFromHM(HashMap<Long, Double> hmForAmountSUm){
        List<SummaryByProduct> summaryByProducts = new ArrayList<>();
        hmForAmountSUm.forEach((aLong, aDouble) -> {
            SummaryByProduct summaryByProduct = new SummaryByProduct();
            summaryByProduct.setProductName(Store.getProductNameById(aLong));
            summaryByProduct.setTotalAmount(aDouble);
            summaryByProducts.add(summaryByProduct);
        });
        return summaryByProducts;
    }

    protected List<SummaryByCity> buildSummaryCityFromHM(HashMap<String, Double> hmForAmountSUm){
        List<SummaryByCity> summaryByCities = new ArrayList<>();
        hmForAmountSUm.forEach((aString, aDouble) -> {
            SummaryByCity summaryByCity = new SummaryByCity();
            summaryByCity.setCityName(aString);
            summaryByCity.setTotalAmount(aDouble);
            summaryByCities.add(summaryByCity);
        });
        return summaryByCities;
    }

    protected File getProductFileInFolder(final String folderLocation) {
        File folder = new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException("configured folderlocation for product is not a directory " + folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length < 1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file " + folderLocation);
        return folder.listFiles()[0];
    }

    protected File[] getUnreadTransactionFiles(final String folderLocation) {
        File folder = new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException("configured folderlocation for product is not a directory " + folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length < 1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file " + folderLocation);
        return Arrays.stream(folder.listFiles()).filter(file -> !Store.isFileProcessed(file)).toArray(File[]::new);
    }
}
