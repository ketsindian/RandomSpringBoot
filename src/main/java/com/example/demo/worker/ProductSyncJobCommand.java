package com.example.demo.worker;

import com.example.demo.service.ITransactionService;
import com.example.demo.store.Store;
import com.example.demo.utils.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProductSyncJobCommand implements SyncJobCommandInterface {

    @Value("${app.productsynccommand.intervalindays}")
    private int intervalInDays;


    final
    ITransactionService transactionService;

    public ProductSyncJobCommand(@Qualifier("imdbService") ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    Logger logger = LoggerFactory.getLogger(ProductSyncJobCommand.class);

    @Override
    public TimeUnit getIntervalUnit() {
        return TimeUnit.DAYS;
    }

    @Override
    public long getRecurringInterval() {
        return this.intervalInDays;
    }

    @Override
    public void run() {
        try {
            logger.info("ProductSyncJobCommand triggered");
            transactionService.syncProducts();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
