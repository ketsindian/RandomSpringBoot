package com.example.demo.worker;

import com.example.demo.service.ITransactionService;
import com.example.demo.store.Store;
import com.example.demo.utils.TransactionException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProductSyncJobCommand implements SyncJobCommandInterface {

    final
    ITransactionService transactionService;

    public ProductSyncJobCommand(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public TimeUnit getIntervalUnit() {
        return TimeUnit.DAYS;
    }

    @Override
    public long getRecurringInterval() {
        return 5;
    }

    @Override
    public void run() {
        try {
            Store.addProduct(transactionService.getStaticProductData());
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
