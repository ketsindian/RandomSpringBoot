package com.example.demo.worker;

import com.example.demo.service.ITransactionService;
import com.example.demo.store.Store;
import com.example.demo.utils.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProductSyncJobCommand implements SyncJobCommandInterface {

    final
    ITransactionService transactionService;

    public ProductSyncJobCommand(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    Logger logger = LoggerFactory.getLogger(ProductSyncJobCommand.class);

    @Override
    public TimeUnit getIntervalUnit() {
        return TimeUnit.DAYS;
    }

    @Override
    public long getRecurringInterval() {
        return 2;
    }

    @Override
    public void run() {
        try {
            logger.info("ProductSyncJobCommand triggered");
            Store.addProduct(transactionService.getStaticProductDataFromFile());
            logger.info("ProductSyncJobCommand synced products");

        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
